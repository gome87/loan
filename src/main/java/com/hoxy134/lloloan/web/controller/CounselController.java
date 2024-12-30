package com.hoxy134.lloloan.web.controller;

import com.hoxy134.lloloan.common.controller.AbstractController;
import com.hoxy134.lloloan.common.dto.ResponseDTO;
import com.hoxy134.lloloan.web.dto.CounselDTO;
import com.hoxy134.lloloan.web.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CounselController extends AbstractController {

    private final CounselService counselService;

    @PostMapping("/counsels")
    public ResponseDTO<CounselDTO.Response> create(@RequestBody CounselDTO.Request request) {
        return ok(counselService.create(request));
    }

    @GetMapping("/counsels/{counselId}")
    public ResponseDTO<CounselDTO.Response> getFindId(@PathVariable Long counselId) {
        return ok(counselService.getFindId(counselId));
    }

    @PutMapping("/counsels/{counselId}")
    public ResponseDTO<CounselDTO.Response> update(@PathVariable Long counselId, @RequestBody CounselDTO.Request request) {
        return ok(counselService.update(counselId, request));
    }

    @DeleteMapping("/counsels/{counselId}")
    public ResponseDTO<CounselDTO.Response> delete(@PathVariable Long counselId) {
        counselService.delete(counselId);
        return ok();
    }

}
