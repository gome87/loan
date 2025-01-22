package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.common.exception.BaseException;
import com.hoxy134.lloloan.common.exception.ResultType;
import com.hoxy134.lloloan.db.entity.Application;
import com.hoxy134.lloloan.db.entity.Balance;
import com.hoxy134.lloloan.db.entity.Entry;
import com.hoxy134.lloloan.db.entity.Repayment;
import com.hoxy134.lloloan.db.repository.ApplicationRepository;
import com.hoxy134.lloloan.db.repository.EntryRepository;
import com.hoxy134.lloloan.db.repository.RepaymentRepository;
import com.hoxy134.lloloan.web.dto.BalanceDTO;
import com.hoxy134.lloloan.web.dto.RepaymentDTO;
import com.hoxy134.lloloan.web.service.BalanceService;
import com.hoxy134.lloloan.web.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service("repaymentService")
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {

    private final ModelMapper modelMapper;

    private final RepaymentRepository repaymentRepository;
    private final ApplicationRepository applicationRepository;
    private final EntryRepository entryRepository;
    private final BalanceService balanceService;

    @Override
    public RepaymentDTO.Response create(Long applicationId, RepaymentDTO.Request request) {

        // 검증
        // 1. 계약을 완료한 신청정보
        // 2. 집행이 되어있어야 함
        if(!isRepayableAplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Repayment repayment = modelMapper.map(request, Repayment.class);
        repayment.setApplicationId(applicationId);

        repaymentRepository.save(repayment);

        // 잔고
        BalanceDTO.Response updatedBalace = balanceService.repaymentUpdate(applicationId,
                BalanceDTO.RepaymentRequest
                        .builder()
                        .repaymentAmount(repayment.getRepaymentAmount())
                        .type(BalanceDTO.RepaymentRequest.RepaymentType.REMOVE)
                        .build());

        RepaymentDTO.Response response = modelMapper.map(repayment, RepaymentDTO.Response.class);

        response.setBalance(updatedBalace.getBalance());

        return response;
    }

    @Override
    public List<RepaymentDTO.ListResponse> getFindId(Long applicationId) {
        List<Repayment> repayments = repaymentRepository.findAllByApplicationId(applicationId);
        return repayments.stream().map(r -> modelMapper.map(r, RepaymentDTO.ListResponse.class)).collect(Collectors.toList());
    }

    @Override
    public RepaymentDTO.UpdateResponse update(Long repaymentId, RepaymentDTO.Request request) {

        Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(() ->{
           throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        Long applicationId = repayment.getApplicationId();
        BigDecimal beforeRepaymentAmount = repayment.getRepaymentAmount();

        balanceService.repaymentUpdate(applicationId,
                BalanceDTO.RepaymentRequest.builder()
                        .repaymentAmount(beforeRepaymentAmount)
                        .type(BalanceDTO.RepaymentRequest.RepaymentType.ADD)
                        .build());


        repayment.setRepaymentAmount(request.getRepaymentAmount());
        repaymentRepository.save(repayment);

        BalanceDTO.Response updateBalance = balanceService.repaymentUpdate(applicationId,
                BalanceDTO.RepaymentRequest.builder()
                        .repaymentAmount(repayment.getRepaymentAmount())
                        .type(BalanceDTO.RepaymentRequest.RepaymentType.REMOVE)
                        .build());

        return RepaymentDTO.UpdateResponse.builder()
                .applicationId(applicationId)
                .beforeRepaymentAmount(beforeRepaymentAmount)
                .afterRepaymentAmount(repayment.getRepaymentAmount())
                .balance(updateBalance.getBalance())
                .createdAt(repayment.getCreatedAt())
                .updatedAt(repayment.getUpdatedAt())
                .build();
    }

    @Override
    public void delete(Long repaymentId) {
        Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        Long applicationId = repayment.getApplicationId();
        BigDecimal removeRepaymentAmount = repayment.getRepaymentAmount();

        balanceService.repaymentUpdate(applicationId
                , BalanceDTO.RepaymentRequest.builder()
                        .repaymentAmount(removeRepaymentAmount)
                        .type(BalanceDTO.RepaymentRequest.RepaymentType.ADD)
                        .build());

        repayment.setIsDeleted(true);

        repaymentRepository.save(repayment);
    }

    private boolean isRepayableAplication(Long applicationId) {
        Optional<Application> existedApplication = applicationRepository.findById(applicationId);
        if(existedApplication.isEmpty()) {
            return false;
        }

        if(existedApplication.get().getContractedAt() == null){
            return false;
        }

        Optional<Entry> existedEntry = entryRepository.findByApplicationId(applicationId);

        return existedEntry.isPresent();
    }

}
