package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.common.exception.BaseException;
import com.hoxy134.lloloan.common.exception.ResultType;
import com.hoxy134.lloloan.db.entity.AcceptTerms;
import com.hoxy134.lloloan.db.entity.Application;
import com.hoxy134.lloloan.db.entity.Terms;
import com.hoxy134.lloloan.db.repository.AcceptTermsRepository;
import com.hoxy134.lloloan.db.repository.ApplicationRepository;
import com.hoxy134.lloloan.db.repository.JudgmentRepository;
import com.hoxy134.lloloan.db.repository.TermsRepository;
import com.hoxy134.lloloan.web.dto.ApplicationDTO;
import com.hoxy134.lloloan.web.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("applicationService")
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ModelMapper modelMapper;
    private final ApplicationRepository applicationRepository;
    private final TermsRepository termsRepository;
    private final AcceptTermsRepository acceptTermsRepository;
    private final JudgmentRepository judgmentRepository;

    @Override
    public ApplicationDTO.Response create(ApplicationDTO.Request request) {

        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application applied = applicationRepository.save(application);

        return modelMapper.map(applied, ApplicationDTO.Response.class);
    }

    @Override
    public ApplicationDTO.Response findById(Long applicationId) {

        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        return modelMapper.map(application, ApplicationDTO.Response.class);
    }

    @Override
    public ApplicationDTO.Response update(Long applicationId, ApplicationDTO.Request request) {

        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setName(request.getName());
        application.setCellPhone(request.getCellPhone());
        application.setEmail(request.getEmail());
        application.setHopeAmount(request.getHopeAmount());

        applicationRepository.save(application);

        return modelMapper.map(application, ApplicationDTO.Response.class);
    }

    @Override
    public void delete(Long applicationId) {

        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        application.setIsDeleted(true);

        applicationRepository.save(application);
    }

    @Override
    public Boolean acceptTerms(Long applicationId, ApplicationDTO.AcceptTermsRequest request) {

        // 신청 정보
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        // 약관 조회
        List<Terms> termsList = termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"));
        if(termsList.isEmpty()){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // 약관 개수 동일 여부
        List<Long> acceptTermsIds = request.getAcceptTermsIds();
        if(termsList.size() != acceptTermsIds.size()){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // 수신 받은 약관번호 정렬
        Collections.sort(acceptTermsIds);

        // 약관 ID만 조회
        List<Long> termsIds = termsList.stream().map(Terms::getTermsId).collect(Collectors.toList());

        // 내용이 전체 포함되는지 확인
        if(!termsIds.containsAll(acceptTermsIds)){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        for(Long tersId : acceptTermsIds){
            AcceptTerms acceptEntity = AcceptTerms.builder()
                    .termsId(tersId)
                    .applicationId(applicationId)
                    .build();

            acceptTermsRepository.save(acceptEntity);
        }

        return true;
    }

    // 대출 계약 기능 구현
    @Override
    public ApplicationDTO.Response contract(Long applicationId) {

        // 신청 정보가 있는지
        Application  application = applicationRepository.findById(applicationId).orElseThrow(() -> {
                throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        // 심사 정보가 있는지
        judgmentRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        // 승인 금액 > 0
        if(application.getApprovalAmount() == null || application.getApprovalAmount().compareTo(BigDecimal.ZERO) == 0){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // 계약 체결
        application.setContractedAt(LocalDateTime.now());
        applicationRepository.save(application);


        return null;
    }

}
