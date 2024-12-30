package com.hoxy134.lloloan.web.service;

import com.hoxy134.lloloan.web.dto.ApplicationDTO;

public interface ApplicationService {

    ApplicationDTO.Response create(ApplicationDTO.Request request);

    ApplicationDTO.Response findById(Long applicationId);

    ApplicationDTO.Response update(Long applicationId, ApplicationDTO.Request request);

    void delete(Long applicationId);

}
