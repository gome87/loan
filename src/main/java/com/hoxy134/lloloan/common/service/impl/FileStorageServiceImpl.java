package com.hoxy134.lloloan.common.service.impl;

import com.hoxy134.lloloan.common.exception.BaseException;
import com.hoxy134.lloloan.common.exception.ResultType;
import com.hoxy134.lloloan.common.service.FileStorageService;
import com.hoxy134.lloloan.db.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Slf4j
@Service("fileStorageService")
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    private final ApplicationRepository applicationRepository;

    @Override
    public void save(Long applicationId, MultipartFile file) {

        if(!isPresentApplication(applicationId)){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        try {
            // 특정 디렉토리가 없으면 생성
            String applicationPath = uploadPath.concat(File.separator + applicationId);
            Path directoryPath = Path.of(applicationPath);
            if(!Files.exists(directoryPath)){
                Files.createDirectories(directoryPath);
            }

            Files.copy(file.getInputStream(), Paths.get(applicationPath).resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public Resource load(Long applicationId, String fileName) {

        if(!isPresentApplication(applicationId)){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        try{
            String applicationPath = uploadPath.concat(File.separator + applicationId);

            Path file = Paths.get(applicationPath).resolve(fileName);

            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new BaseException(ResultType.NOT_EXIST);
            }
        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public Stream<Path> loadAll(Long applicationId) {

        if(!isPresentApplication(applicationId)){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        try{
            String applicationPath = uploadPath.concat(File.separator + applicationId);

            return Files.walk(Paths.get(applicationPath), 1).filter(path -> !path.equals(Paths.get(applicationPath)));
        } catch (Exception e) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteAll(Long applicationId) {

        if(!isPresentApplication(applicationId)){
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        String applicationPath = uploadPath.concat(File.separator + applicationId);

        FileSystemUtils.deleteRecursively(Paths.get(applicationPath).toFile());
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }

}
