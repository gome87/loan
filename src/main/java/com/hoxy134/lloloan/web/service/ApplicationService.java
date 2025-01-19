package com.hoxy134.lloloan.web.service;

import com.hoxy134.lloloan.web.dto.ApplicationDTO;

public interface ApplicationService {

    ApplicationDTO.Response create(ApplicationDTO.Request request);

    ApplicationDTO.Response findById(Long applicationId);

    ApplicationDTO.Response update(Long applicationId, ApplicationDTO.Request request);

    void delete(Long applicationId);

    Boolean acceptTerms(Long applicationId, ApplicationDTO.AcceptTermsRequest request);

    // 대출 계약 기능 구현
    ApplicationDTO.Response contract(Long applicationId);

}
