package com.hoxy134.lloloan.web.controller;

import com.hoxy134.lloloan.common.controller.AbstractController;
import com.hoxy134.lloloan.common.dto.ResponseDTO;
import com.hoxy134.lloloan.common.service.FileStorageService;
import com.hoxy134.lloloan.web.dto.ApplicationDTO;
import com.hoxy134.lloloan.web.dto.FileDTO;
import com.hoxy134.lloloan.web.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ApplicationController extends AbstractController {

    private final ApplicationService applicationService;
    private final FileStorageService fileStorageService;

    @PostMapping("/applications")
    public ResponseDTO<ApplicationDTO.Response> create(@RequestBody ApplicationDTO.Request request) {
        return ok(applicationService.create(request));
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseDTO<ApplicationDTO.Response> findById(@PathVariable Long applicationId) {
        return ok(applicationService.findById(applicationId));
    }

    @PutMapping("/applications/{applicationId}")
    public ResponseDTO<ApplicationDTO.Response> update(@PathVariable Long applicationId, @RequestBody ApplicationDTO.Request request) {
        return ok(applicationService.update(applicationId, request));
    }

    @DeleteMapping("/applications/{applicationId}")
    public ResponseDTO<ApplicationDTO.Response> delete(@PathVariable Long applicationId) {
        applicationService.delete(applicationId);
        return ok();
    }

    @PostMapping("/applications/{applicationId}/terms")
    public ResponseDTO<Boolean> acceptTerms(@PathVariable Long applicationId, @RequestBody ApplicationDTO.AcceptTermsRequest request){
        return ok(applicationService.acceptTerms(applicationId, request));
    }

    @PostMapping("/applications/{applicationId}/files")
    public ResponseDTO<Void> uploadFiles(@PathVariable Long applicationId, MultipartFile file) throws IllegalStateException {
        fileStorageService.save(applicationId, file);
        return ok();
    }

    @GetMapping("/applications/{applicationId}/files")
    public ResponseEntity<Resource> download(@PathVariable Long applicationId, @RequestParam(value = "fileName") String fileName)  throws IllegalStateException, IOException {
        Resource file = fileStorageService.load(applicationId, fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/applications/{applicationId}/files/infos")
    public ResponseDTO<List<FileDTO>> getFileInfos(@PathVariable Long applicationId) {
        List<FileDTO> fileInfos = fileStorageService.loadAll(applicationId).map(path -> {
            String fileName = path.getFileName().toString();
            return FileDTO.builder()
                    .name(fileName)
                    .url(MvcUriComponentsBuilder.fromMethodName(ApplicationController.class, "download", applicationId, fileName).build().toString())
                    .build();
        }).collect(Collectors.toList());
        return ok(fileInfos);
    }

    @DeleteMapping("/applications/{applicationId}/files")
    public ResponseDTO<Void> deleteAll(@PathVariable Long applicationId) {
        fileStorageService.deleteAll(applicationId);
        return ok();
    }

    @PutMapping("/applications/{applicationId}/contract")
    public ResponseDTO<ApplicationDTO.Response> contract(@PathVariable Long applicationId) {
        return ok(applicationService.contract(applicationId));
    }

}
