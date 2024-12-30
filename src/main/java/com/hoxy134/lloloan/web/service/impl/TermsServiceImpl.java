package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.db.entity.Terms;
import com.hoxy134.lloloan.db.repository.TermsRepository;
import com.hoxy134.lloloan.web.dto.TermsDTO;
import com.hoxy134.lloloan.web.service.TermsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("termsService")
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final ModelMapper modelMapper;
    private final TermsRepository termsRepository;

    @Override
    public TermsDTO.Response create(TermsDTO.Request request) {

        Terms terms =  modelMapper.map(request, Terms.class);

        Terms created = termsRepository.save(terms);

        return modelMapper.map(created, TermsDTO.Response.class);
    }

    @Override
    public List<TermsDTO.Response> findAll() {

        List<Terms> termsList = termsRepository.findAll();

        return termsList.stream().map(t -> modelMapper.map(t, TermsDTO.Response.class)).collect(Collectors.toList());
    }


}
