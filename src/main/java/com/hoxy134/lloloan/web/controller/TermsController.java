package com.hoxy134.lloloan.web.controller;

import com.hoxy134.lloloan.common.controller.AbstractController;
import com.hoxy134.lloloan.common.dto.ResponseDTO;
import com.hoxy134.lloloan.web.dto.TermsDTO;
import com.hoxy134.lloloan.web.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TermsController extends AbstractController {

    private final TermsService termsService;

    @PostMapping("/terms")
    public ResponseDTO<TermsDTO.Response> create(@RequestBody TermsDTO.Request request) {
        return ok(termsService.create(request));
    }

    @GetMapping("/terms")
    public ResponseDTO<List<TermsDTO.Response>> getAll() {
        return ok(termsService.findAll());
    }
}
