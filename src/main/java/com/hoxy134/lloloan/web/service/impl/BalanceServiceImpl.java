package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.common.exception.BaseException;
import com.hoxy134.lloloan.common.exception.ResultType;
import com.hoxy134.lloloan.db.entity.Balance;
import com.hoxy134.lloloan.db.repository.ApplicationRepository;
import com.hoxy134.lloloan.db.repository.BalanceRepository;
import com.hoxy134.lloloan.web.dto.BalanceDTO;
import com.hoxy134.lloloan.web.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service("balanceService")
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final ModelMapper modelMapper;
    private final BalanceRepository balanceRepository;


    @Override
    public BalanceDTO.Response create(Long applicationId, BalanceDTO.Request request) {

        BigDecimal entryAmount = request.getEnrtyAmount();

        Balance balance = modelMapper.map(request, Balance.class);
        balance.setApplicationId(applicationId);
        balance.setBalance(entryAmount);

        // 한건만 진행할거라서 존재할 경우 업데이트
        balanceRepository.findByApplicationId(applicationId).ifPresent(b -> {
            balance.setBalanceId(b.getBalanceId());
            balance.setIsDeleted(b.getIsDeleted());
            balance.setCreatedAt(b.getCreatedAt());
            balance.setUpdatedAt(b.getUpdatedAt());
        });

        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(saved, BalanceDTO.Response.class);
    }

    @Override
    public BalanceDTO.Response update(Long applicationId, BalanceDTO.UpdateRequest request) {

        // balance 존재 여부
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        // balance 값 변경
        BigDecimal beforeEntryAmount = request.getBeforeEnrtyAmount();
        BigDecimal afterEntryAmount = request.getAfterEnrtyAmount();
        BigDecimal updateBalance = balance.getBalance();

        updateBalance = afterEntryAmount.subtract(beforeEntryAmount).add(afterEntryAmount);
        balance.setBalance(updateBalance);

        Balance updated = balanceRepository.save(balance);

        return modelMapper.map(updated, BalanceDTO.Response.class);
    }

    @Override
    public BalanceDTO.Response repaymentUpdate(Long applicationId, BalanceDTO.RepaymentRequest request) {
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        BigDecimal updateBalance = balance.getBalance();
        BigDecimal repaymentAmount = request.getRepaymentAmount();

        // 상환
        if(request.getType().equals(BalanceDTO.RepaymentRequest.RepaymentType.ADD)){
            updateBalance = updateBalance.add(repaymentAmount);
        } else {
            updateBalance = updateBalance.subtract(repaymentAmount);
        }

        balance.setBalance(updateBalance);

        Balance updated = balanceRepository.save(balance);

        return modelMapper.map(updated, BalanceDTO.Response.class);
    }


}
