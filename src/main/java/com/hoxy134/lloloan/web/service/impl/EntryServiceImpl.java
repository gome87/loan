package com.hoxy134.lloloan.web.service.impl;

import com.hoxy134.lloloan.common.exception.BaseException;
import com.hoxy134.lloloan.common.exception.ResultType;
import com.hoxy134.lloloan.db.entity.Application;
import com.hoxy134.lloloan.db.entity.Entry;
import com.hoxy134.lloloan.db.repository.ApplicationRepository;
import com.hoxy134.lloloan.db.repository.EntryRepository;
import com.hoxy134.lloloan.web.dto.BalanceDTO;
import com.hoxy134.lloloan.web.dto.EntryDTO;
import com.hoxy134.lloloan.web.service.BalanceService;
import com.hoxy134.lloloan.web.service.EntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service("entryService")
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final ModelMapper modelMapper;
    private final EntryRepository entryRepository;
    private final ApplicationRepository applicationRepository;

    private final BalanceService balanceService;


    @Override
    public EntryDTO.Response create(Long applicationId, EntryDTO.Request request) {

        // 계약 체결 여부 검증
        if(!isContractedApplication(applicationId)){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Entry entry = modelMapper.map(request, Entry.class);
        entry.setApplicationId(applicationId);

        entryRepository.save(entry);


        // 대출 잔고 관리
        balanceService.create(applicationId,
                BalanceDTO.Request.builder()
                        .enrtyAmount(request.getEntryAmount())
                        .build());


        return modelMapper.map(entry, EntryDTO.Response.class);
    }

    @Override
    public EntryDTO.Response getFindId(Long applicationId) {

        Optional<Entry> entry = entryRepository.findByApplicationId(applicationId);

        if(entry.isPresent()){
           return modelMapper.map(entry, EntryDTO.Response.class);
        } else {
            return null;
        }
    }

    @Override
    public EntryDTO.UpdateResponse update(Long entryId, EntryDTO.Request request) {

        // Entry 존재여부
        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });


        // before -> after
        BigDecimal beforeEntryAmount = entry.getEntryAmount();
        entry.setEntryAmount(request.getEntryAmount());

        entryRepository.save(entry);


        // balance 변경
        Long applicationId = entry.getApplicationId();
        balanceService.update(applicationId,
                BalanceDTO.UpdateRequest.builder()
                        .beforeEnrtyAmount(beforeEntryAmount)
                        .afterEnrtyAmount(request.getEntryAmount())
                        .build());


        // response
        return EntryDTO.UpdateResponse.builder()
                .entryId(entryId)
                .applicationId(applicationId)
                .beforeEntryAmount(beforeEntryAmount)
                .afterEntryAmount(request.getEntryAmount())
                .build();
    }

    @Override
    public void delete(Long entryId) {
        // Entry 존재여부
        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        });

        entry.setIsDeleted(true);
        entryRepository.save(entry);

        BigDecimal beforeEntryAmount = entry.getEntryAmount();
        Long applicationId = entry.getApplicationId();

        balanceService.update(applicationId,
                BalanceDTO.UpdateRequest.builder()
                        .beforeEnrtyAmount(beforeEntryAmount)
                        .afterEnrtyAmount(BigDecimal.ZERO)
                        .build());
    }

    private boolean isContractedApplication(Long applicationId) {
        Optional<Application> existed = applicationRepository.findById(applicationId);
        if (existed.isEmpty()) {
            return false;
        }
        return existed.get().getContractedAt() != null;
    }

}
