package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.db.entity.Terms;
import com.hoxy134.lloloan.db.repository.TermsRepository;
import com.hoxy134.lloloan.web.dto.TermsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TermsServiceImplTest {

    @InjectMocks
    TermsServiceImpl termsService;

    @Mock
    private TermsRepository termsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewTermsEntity_When_RequestTerm() {

        Terms entity = Terms.builder()
                .name("대출 이용 약관")
                .termsDetailUrl("https://abc-storage.acc/adsfafd")
                .build();

        TermsDTO.Request request = TermsDTO.Request.builder()
                .name("대출 이용 약관")
                .termsDetailUrl("https://abc-storage.acc/adsfafd")
                .build();

        when(termsRepository.save(ArgumentMatchers.any(Terms.class))).thenReturn(entity);

        TermsDTO.Response actual = termsService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
        assertThat(actual.getTermsDetailUrl()).isSameAs(entity.getTermsDetailUrl());
    }

    @Test
    void Should_ReturnAllResponseOfExistTermsEntities_When_RequestTermsList(){

        Terms entityA = Terms.builder()
                .name("대출 이용약관1")
                .termsDetailUrl("https://abc-storage.acc/adsfafd1")
                .build();

        Terms entityB = Terms.builder()
                .name("대출 이용약관2")
                .termsDetailUrl("https://abc-storage.acc/gesgdvs2")
                .build();

        List<Terms> list = Arrays.asList(entityA, entityB);

        when(this.termsRepository.findAll()).thenReturn(list);

        List<TermsDTO.Response> actual = termsService.findAll();

        assertThat(actual.size()).isSameAs(list.size());

    }

}