package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.db.entity.Application;
import com.hoxy134.lloloan.db.entity.Judgment;
import com.hoxy134.lloloan.db.repository.ApplicationRepository;
import com.hoxy134.lloloan.db.repository.JudgmentRepository;
import com.hoxy134.lloloan.web.dto.ApplicationDTO;
import com.hoxy134.lloloan.web.dto.JudgmentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JudgmentServiceImplTest {

    @InjectMocks
    private JudgmentServiceImpl judgmentService;

    @Mock
    private JudgmentRepository judgmentRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewJudgmentEntity_When_RequestNewJudgment() {

        Judgment judgment = Judgment.builder()
                .applicationId(1L)
                .name("Member Kim")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        JudgmentDTO.Request request = JudgmentDTO.Request.builder()
                .applicationId(1L)
                .name("Member Kim")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        // application find
        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().build()));

        // judgment save
        when(judgmentRepository.save(ArgumentMatchers.any(Judgment.class))).thenReturn(judgment);

        // service 호출
        JudgmentDTO.Response actual = judgmentService.create(request);

        // 검증
        assertThat(actual.getName()).isSameAs(judgment.getName());
        assertThat(actual.getApplicationId()).isSameAs(judgment.getApplicationId());
        assertThat(actual.getApprovalAmount()).isSameAs(judgment.getApprovalAmount());
    }

    @Test
    void Should_ReturnResponseOfExistJudgmentEntity_When_RequestExistJudgmentId() {

        Judgment judgment = Judgment.builder()
                .judgmentId(1L)
                .build();

        // judgment find
        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(judgment));

        JudgmentDTO.Response actual = judgmentService.getFindId(1L);

        // 검증
        assertThat(actual.getJudgmentId()).isSameAs(1L);
    }

    @Test
    void Should_ReturnResponseOfExistJudgmentEntity_When_RequestExistApplicationId() {

        Judgment judgment = Judgment.builder()
                .judgmentId(1L)
                .build();

        Application application = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(application));
        when(judgmentRepository.findByApplicationId(1L)).thenReturn(Optional.ofNullable(judgment));

        JudgmentDTO.Response actual = judgmentService.getJudgmentOfApplication(1L);

        // 검증
        assertThat(actual.getJudgmentId()).isSameAs(1L);
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistJudgmentEntity_When_RequestUpdateExistJudgmentInfo() {

        Judgment judgment = Judgment.builder()
                .judgmentId(1L)
                .name("Member Kim")
                .approvalAmount(BigDecimal.valueOf(500000))
                .build();

        JudgmentDTO.Request request = JudgmentDTO.Request.builder()
                .name("Member Kang")
                .approvalAmount(BigDecimal.valueOf(10000000))
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(judgment));
        when(judgmentRepository.save(ArgumentMatchers.any(Judgment.class))).thenReturn(judgment);

        JudgmentDTO.Response actual = judgmentService.update(1L, request);

        // 검증
        assertThat(actual.getJudgmentId()).isSameAs(1L);
        assertThat(actual.getName()).isSameAs(request.getName());
        assertThat(actual.getApprovalAmount()).isSameAs(request.getApprovalAmount());
    }

    @Test
    void Should_DeletedJudgmentEntity_When_RequestDeleteExistJudgmentInfo() {

        Judgment judgment = Judgment.builder()
                .judgmentId(1L)
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(judgment));
        when(judgmentRepository.save(ArgumentMatchers.any(Judgment.class))).thenReturn(judgment);

        judgmentService.delete(1L);

        // 검증
        assertThat(judgment.getIsDeleted()).isTrue();
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistApplicationEntity_When_RequestGrantApprovalAmountOfJudgmentInfo() {

        Judgment judgment = Judgment.builder()
                .judgmentId(1L)
                .applicationId(1L)
                .name("Member Kim")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        Application application = Application.builder()
                .applicationId(1L)
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        when(judgmentRepository.findById(1L)).thenReturn(Optional.ofNullable(judgment));
        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(application));
        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(application);

        ApplicationDTO.GrantAmount actual = judgmentService.grant(1L);

        // 검증
        assertThat(actual.getApplicationId()).isSameAs(1L);
        assertThat(actual.getApprovalAmount()).isSameAs(judgment.getApprovalAmount());

    }

}