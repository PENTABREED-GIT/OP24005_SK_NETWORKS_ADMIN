package com.skn.admin.example.controller;

import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.example.service.ExampleApiService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/example")
public class ExampleApiController {

    private final ExampleApiService exampleApiService;

    @Operation(description = "등록 에시")
    @PostMapping("")
    public APIDataResponse<String> createExample() {

        return APIDataResponse.of(Boolean.toString(true));
    }

    @Operation(description = "수정 에시")
    @PutMapping("")
    public APIDataResponse<String> putExample() {

        return APIDataResponse.of(Boolean.toString(true));
    }

    @Operation(description = "등록 에시")
    @DeleteMapping("")
    public APIDataResponse<String> deleteExample() {

        return APIDataResponse.of(Boolean.toString(true));
    }
}
