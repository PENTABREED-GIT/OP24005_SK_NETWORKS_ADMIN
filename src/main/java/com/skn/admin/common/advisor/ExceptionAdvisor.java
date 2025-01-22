package com.skn.admin.common.advisor;

import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.config.api.exception.GeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.validation.ObjectError;

@ControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIDataResponse<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(APIDataResponse.of("Validation error: " + String.join(", ", errorMessages)));
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<String> handleGeneralException(GeneralException ex) {
        // GeneralException에서 메시지를 가져와 반환
        return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    }
}
