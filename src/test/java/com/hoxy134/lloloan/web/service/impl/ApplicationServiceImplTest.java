package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.common.exception.BaseException;
import com.hoxy134.lloloan.db.entity.AcceptTerms;
import com.hoxy134.lloloan.db.entity.Application;
import com.hoxy134.lloloan.db.entity.Terms;
import com.hoxy134.lloloan.db.repository.AcceptTermsRepository;
import com.hoxy134.lloloan.db.repository.ApplicationRepository;
import com.hoxy134.lloloan.db.repository.TermsRepository;
import com.hoxy134.lloloan.web.dto.ApplicationDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private TermsRepository termsRepository;

    @Mock
    private AcceptTermsRepository acceptTermsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewApplicationEntity_When_RequestCreateApplication() {

        Application entity = Application.builder()
                .name("Member Kim")
                .cellPhone("010-1111-2222")
                .email("mail@abcd.efg")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        ApplicationDTO.Request request = ApplicationDTO.Request.builder()
                .name("Member Kim")
                .cellPhone("010-1111-2222")
                .email("mail@abcd.efg")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);

        ApplicationDTO.Response actual = applicationService.create(request);

        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
        assertThat(actual.getName()).isSameAs(entity.getName());
    }

    @Test
    void Should_ReturnResponseOfExistApplicationEntity_When_RequestExistApplicationId() {

        Long findId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        ApplicationDTO.Response actual = applicationService.findById(findId);

        assertThat(actual.getApplicationId()).isSameAs(findId);
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistApplicationEntity_when_RequestUpdateExistApplicationInfo() {

        Long findId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        ApplicationDTO.Request request = ApplicationDTO.Request.builder()
                .hopeAmount(BigDecimal.valueOf(5000000))
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        ApplicationDTO.Response actual = applicationService.update(findId, request);

        assertThat(actual.getApplicationId()).isSameAs(findId);
        assertThat(actual.getHopeAmount()).isSameAs(request.getHopeAmount());
    }

    @Test
    void Should_ReturnResponseOfApplicationEntity_When_RequestDeleteExistApplicationInfo() {
        Long targetId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);
        when(applicationRepository.findById(targetId)).thenReturn(Optional.ofNullable(entity));

        applicationService.delete(targetId);

        assertThat(entity.getIsDeleted()).isSameAs(true);
    }

    @Test
    void Should_AddAcceptTerms_When_RequestAcceptTermsOfApplication() {
        Terms entityA = Terms.builder()
                .termsId(1L)
                .name("대출 이용 약관 1")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
                .build();

        Terms entityB = Terms.builder()
                .termsId(2L)
                .name("대출 이용 약관 2")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L, 2L);

        ApplicationDTO.AcceptTermsRequest request = ApplicationDTO.AcceptTermsRequest.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));
        when(acceptTermsRepository.save(ArgumentMatchers.any(AcceptTerms.class))).thenReturn(AcceptTerms.builder().build());

        Boolean actual = applicationService.acceptTerms(findId, request);

        assertThat(actual).isTrue();
    }

    @Test
    void Should_ThrowException_When_RequestNotAllAcceptTermsOfApplication() {
        Terms entityA = Terms.builder()
                .termsId(1L)
                .name("대출 이용 약관 1")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
                .build();

        Terms entityB = Terms.builder()
                .termsId(2L)
                .name("대출 이용 약관 2")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L);

        ApplicationDTO.AcceptTermsRequest request = ApplicationDTO.AcceptTermsRequest.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));

        Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(1L, request));
    }

    @Test
    void Should_ThrowException_When_RequestNotExistAcceptTermsOfApplication() {
        Terms entityA = Terms.builder()
                .termsId(1L)
                .name("대출 이용 약관 1")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdddads")
                .build();

        Terms entityB = Terms.builder()
                .termsId(2L)
                .name("대출 이용 약관 2")
                .termsDetailUrl("https://abc-storage.acc/dslfjdlsfjlsdweqwq")
                .build();

        List<Long> acceptTerms = Arrays.asList(1L, 3L);

        ApplicationDTO.AcceptTermsRequest request = ApplicationDTO.AcceptTermsRequest.builder()
                .acceptTermsIds(acceptTerms)
                .build();

        Long findId = 1L;

        when(applicationRepository.findById(findId)).thenReturn(
                Optional.ofNullable(Application.builder().build())
        );
        when(termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"))).thenReturn(Arrays.asList(entityA, entityB));

        Assertions.assertThrows(BaseException.class, () -> applicationService.acceptTerms(1L, request));
    }

}