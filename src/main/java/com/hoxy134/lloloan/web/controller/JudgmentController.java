package com.hoxy134.lloloan.web.controller;

import com.hoxy134.lloloan.common.controller.AbstractController;
import com.hoxy134.lloloan.common.dto.ResponseDTO;
import com.hoxy134.lloloan.web.dto.ApplicationDTO;
import com.hoxy134.lloloan.web.dto.JudgmentDTO;
import com.hoxy134.lloloan.web.service.JudgmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class JudgmentController extends AbstractController {

    private final JudgmentService judgmentService;

    @PostMapping("/judgments")
    public ResponseDTO<JudgmentDTO.Response> create(@RequestBody JudgmentDTO.Request request){
        return ok(judgmentService.create(request));
    }

    @GetMapping("/judgments/{judgmentId}")
    public ResponseDTO<JudgmentDTO.Response> getFindId(@PathVariable Long judgmentId){
        return ok(judgmentService.getFindId(judgmentId));
    }

    @GetMapping("/judgments/applications/{applicationId}")
    public ResponseDTO<JudgmentDTO.Response> getJudgmentOfApplication(@PathVariable Long applicationId){
        return ok(judgmentService.getJudgmentOfApplication(applicationId));
    }

    @PutMapping("/judgments/{judgmentId}")
    public ResponseDTO<JudgmentDTO.Response> update (@PathVariable Long judgmentId, @RequestBody JudgmentDTO.Request request){
        return ok(judgmentService.update(judgmentId, request));
    }

    @DeleteMapping("/judgments/{judgmentId}")
    public ResponseDTO<Void> delete (@PathVariable Long judgmentId){
        judgmentService.delete(judgmentId);
        return ok();
    }

    @PatchMapping("/judgments/{judgmentId}/grants")
    public ResponseDTO<ApplicationDTO.GrantAmount> grant(@PathVariable Long judgmentId){
        return ok(judgmentService.grant(judgmentId));
    }

}
