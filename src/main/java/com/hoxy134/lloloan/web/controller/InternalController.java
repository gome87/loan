package com.hoxy134.lloloan.web.controller;

import com.hoxy134.lloloan.common.controller.AbstractController;
import com.hoxy134.lloloan.common.dto.ResponseDTO;
import com.hoxy134.lloloan.web.dto.EntryDTO;
import com.hoxy134.lloloan.web.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class InternalController extends AbstractController {

    private final EntryService entryService;


    // 대출 집행 등록
    @PostMapping("/internal/applications/{applicationId}/entries")
    public ResponseDTO<EntryDTO.Response> createEntry(@PathVariable("applicationId") Long applicationId, @RequestBody EntryDTO.Request request) {
        return ok(entryService.create(applicationId, request));
    }

    // 대출 집행 조회
    @GetMapping("/internal/applications/{applicationId}/entries")
    public ResponseDTO<EntryDTO.Response> getFindApplication(@PathVariable("applicationId") Long applicationId) {
        return ok(entryService.getFindId(applicationId));
    }

    // 대출 집행 수정
    @PutMapping("/internal/applications/entries/{entryId}")
    public ResponseDTO<EntryDTO.UpdateResponse> updateEntry(@PathVariable("entryId") Long entryId, @RequestBody EntryDTO.Request request) {
        return ok(entryService.update(entryId, request));
    }

    // 대출 집행 삭제
    @DeleteMapping("/internal/applications/entries/{entryId}")
    public ResponseDTO<Void> deleteEntry(@PathVariable("entryId") Long entryId) {
        entryService.delete(entryId);
        return ok();
    }

}
