package com.hoxy134.lloloan.web.controller;

import com.hoxy134.lloloan.common.controller.AbstractController;
import com.hoxy134.lloloan.common.dto.ResponseDTO;
import com.hoxy134.lloloan.web.dto.EntryDTO;
import com.hoxy134.lloloan.web.dto.RepaymentDTO;
import com.hoxy134.lloloan.web.service.EntryService;
import com.hoxy134.lloloan.web.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class InternalController extends AbstractController {

    private final EntryService entryService;
    private final RepaymentService repaymentService;


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

    // 대출 상환 등록
    @PostMapping("/internal/applications/{applicationId}/repayments")
    public ResponseDTO<RepaymentDTO.Response> createRepayment(@PathVariable("applicationId") Long applicationId, @RequestBody RepaymentDTO.Request request) {
        return ok(repaymentService.create(applicationId, request));
    }

    // 대출 상환 조회
    @GetMapping("/internal/applications/{applicationId}/repayments")
    public ResponseDTO<List<RepaymentDTO.ListResponse>> getFindRepayment(@PathVariable("applicationId") Long applicationId) {
        return ok(repaymentService.getFindId(applicationId));
    }

    // 대출 상환 수정
    @PutMapping("/internal/applications/repayments/{repaymentId}")
    public ResponseDTO<RepaymentDTO.UpdateResponse> updateRepayment(@PathVariable("repaymentId") Long repaymentId, @RequestBody RepaymentDTO.Request request) {
        return ok(repaymentService.update(repaymentId, request));
    }

    // 대출 상환 삭제
    @DeleteMapping("/internal/applications/repayments/{repaymentId}")
    public ResponseDTO<Void> deleteRepayment(@PathVariable("repaymentId") Long repaymentId) {
        repaymentService.delete(repaymentId);
        return ok();
    }

}
