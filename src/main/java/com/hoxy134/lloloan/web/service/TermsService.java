package com.hoxy134.lloloan.web.service;

import com.hoxy134.lloloan.web.dto.TermsDTO;

import java.util.List;

public interface TermsService {

    TermsDTO.Response create(TermsDTO.Request request);

    List<TermsDTO.Response> findAll();

}
