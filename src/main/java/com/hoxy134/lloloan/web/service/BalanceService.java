package com.hoxy134.lloloan.web.service;

import com.hoxy134.lloloan.web.dto.BalanceDTO;

public interface BalanceService {

    BalanceDTO.Response create(Long applicationId, BalanceDTO.Request request);

    BalanceDTO.Response update(Long applicationId, BalanceDTO.UpdateRequest request);

}
