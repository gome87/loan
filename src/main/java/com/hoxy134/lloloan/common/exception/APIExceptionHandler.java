package com.hoxy134.lloloan.common.exception;

import com.hoxy134.lloloan.common.dto.ResponseDTO;
import com.hoxy134.lloloan.common.dto.ResultObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class APIExceptionHandler extends RuntimeException {

    @ExceptionHandler(BaseException.class)
    protected ResponseDTO<ResultObject> handleBaseException(BaseException e,
                                                            HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return new ResponseDTO<>(e);
    }
}
