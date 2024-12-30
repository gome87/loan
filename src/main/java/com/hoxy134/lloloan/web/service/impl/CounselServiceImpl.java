package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.common.exception.BaseException;
import com.hoxy134.lloloan.common.exception.ResultType;
import com.hoxy134.lloloan.db.entity.Counsel;
import com.hoxy134.lloloan.db.repository.CounselRepository;
import com.hoxy134.lloloan.web.dto.CounselDTO;
import com.hoxy134.lloloan.web.service.CounselService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service("counselService")
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService {

    private final ModelMapper modelMapper;
    private final CounselRepository counselRepository;

    @Override
    public CounselDTO.Response create(CounselDTO.Request request) {

        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());

        Counsel rtnEntity = counselRepository.save(counsel);

        return modelMapper.map(rtnEntity, CounselDTO.Response.class);
    }

    @Override
    public CounselDTO.Response getFindId(Long counselId) {

        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        return modelMapper.map(counsel, CounselDTO.Response.class);
    }

    @Override
    public CounselDTO.Response update(Long counselId, CounselDTO.Request request) {

        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        counsel.setName(request.getName());
        counsel.setCellPhone(request.getCellPhone());
        counsel.setEmail(request.getEmail());
        counsel.setMemo(request.getMemo());
        counsel.setAddress(request.getAddress());
        counsel.setAddressDetail(request.getAddressDetail());
        counsel.setZipCode(request.getZipCode());

        counselRepository.save(counsel);

        return modelMapper.map(counsel, CounselDTO.Response.class);
    }

    @Override
    public void delete(Long counselId) {

        Counsel counsel = counselRepository.findById(counselId).orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        counsel.setIsDeleted(true);

        counselRepository.save(counsel);
    }


}
