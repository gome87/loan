package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.db.entity.Counsel;
import com.hoxy134.lloloan.db.repository.CounselRepository;
import com.hoxy134.lloloan.web.dto.CounselDTO;
import com.hoxy134.lloloan.web.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

}
