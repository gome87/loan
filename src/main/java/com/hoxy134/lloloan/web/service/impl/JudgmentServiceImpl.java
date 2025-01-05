package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.common.exception.BaseException;
import com.hoxy134.lloloan.common.exception.ResultType;
import com.hoxy134.lloloan.db.entity.Application;
import com.hoxy134.lloloan.db.entity.Judgment;
import com.hoxy134.lloloan.db.repository.ApplicationRepository;
import com.hoxy134.lloloan.db.repository.JudgmentRepository;
import com.hoxy134.lloloan.web.dto.ApplicationDTO;
import com.hoxy134.lloloan.web.dto.JudgmentDTO;
import com.hoxy134.lloloan.web.service.JudgmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service("judgmentService")
@RequiredArgsConstructor
public class JudgmentServiceImpl implements JudgmentService {

    private final ModelMapper modelMapper;

    private final JudgmentRepository judgmentRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public JudgmentDTO.Response create(JudgmentDTO.Request request) {

        // 신청 정보 검증
        Long applicationId = request.getApplicationId();
        if(!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // request dto -> entity -> save
        Judgment judgment = modelMapper.map(request, Judgment.class);
        Judgment saved = judgmentRepository.save(judgment);

        // save -> response dto
        return modelMapper.map(saved, JudgmentDTO.Response.class);
    }

    @Override
    public JudgmentDTO.Response getFindId(Long judgmentId) {
        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });
        return modelMapper.map(judgment, JudgmentDTO.Response.class);
    }

    @Override
    public JudgmentDTO.Response getJudgmentOfApplication(Long applicationId) {

        if(!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Judgment judgment = judgmentRepository.findByApplicationId(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        return modelMapper.map(judgment, JudgmentDTO.Response.class);
    }

    @Override
    public JudgmentDTO.Response update(Long judgmentId, JudgmentDTO.Request request) {

        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        judgment.setName(request.getName());
        judgment.setApprovalAmount(request.getApprovalAmount());
        Judgment saved = judgmentRepository.save(judgment);

        return modelMapper.map(saved, JudgmentDTO.Response.class);
    }

    @Override
    public void delete(Long judgmentId) {

        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        judgment.setIsDeleted(true);

        judgmentRepository.save(judgment);
    }

    @Override
    public ApplicationDTO.GrantAmount grant(Long judgmentId) {

        Judgment judgment = judgmentRepository.findById(judgmentId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        Long applicationId = judgment.getApplicationId();
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        BigDecimal approvalAmount = judgment.getApprovalAmount();
        application.setApprovalAmount(approvalAmount);

        applicationRepository.save(application);

        return modelMapper.map(application, ApplicationDTO.GrantAmount.class);
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }

}
