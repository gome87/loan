package com.hoxy134.lloloan.web.service;

import com.hoxy134.lloloan.web.dto.RepaymentDTO;

import java.util.List;

public interface RepaymentService {

    RepaymentDTO.Response create(Long applicationId, RepaymentDTO.Request request);

    List<RepaymentDTO.ListResponse> getFindId(Long applicationId);

    RepaymentDTO.UpdateResponse update(Long repaymentId, RepaymentDTO.Request request);

    void delete(Long repaymentId);

}
