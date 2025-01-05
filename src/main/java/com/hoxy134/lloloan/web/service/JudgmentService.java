package com.hoxy134.lloloan.web.service;

import com.hoxy134.lloloan.web.dto.ApplicationDTO;
import com.hoxy134.lloloan.web.dto.JudgmentDTO;

public interface JudgmentService {

    JudgmentDTO.Response create(JudgmentDTO.Request request);

    JudgmentDTO.Response getFindId(Long judgmentId);

    JudgmentDTO.Response getJudgmentOfApplication(Long applicationId);

    JudgmentDTO.Response update(Long judgmentId, JudgmentDTO.Request request);

    void delete(Long judgmentId);

    ApplicationDTO.GrantAmount grant(Long judgmentId);

}
