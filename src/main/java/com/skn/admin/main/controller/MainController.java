package com.skn.admin.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    @Operation(description = "초기화면")
    @GetMapping("/")
    public String main(HttpSession session, Model model) {
        log.debug("manager:{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Map<String, String> reqMap = new HashMap<>();
        String userId = null;
        String resut = "";

        return "index";
    }
}
