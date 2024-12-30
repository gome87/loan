package com.hoxy134.lloloan.web.service;

import com.hoxy134.lloloan.web.dto.CounselDTO;

public interface CounselService {

    CounselDTO.Response create(CounselDTO.Request request);

    CounselDTO.Response getFindId(Long counselId);

    CounselDTO.Response update(Long counselId, CounselDTO.Request request);

    void delete(Long counselId);

}
