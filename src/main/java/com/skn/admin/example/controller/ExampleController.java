package com.skn.admin.example.controller;


import com.skn.admin.example.service.ExamplePageService;
import com.skn.admin.base.etc.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.skn.admin.base.etc.Page.PAGE_BLOCK_SIZE;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExampleController {

    private final ExamplePageService examplePageService;

    @Operation(description = "리스트 정보 불러오기 예시")
    @GetMapping("/example")
    public ModelAndView exampleList(
            @RequestParam Map<String, Object> reqMap,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "perPage", defaultValue = "30") int size,
            Model model
    ) {
        examplePageService.init(reqMap);

        ModelAndView mv = new ModelAndView("example/exampleList");

        examplePageService.selectExampleList(
                reqMap,
                new Page(PAGE_BLOCK_SIZE, page, size),
                model
        );

        return mv;
    }

    @Operation(description = "정보 보기")
    @GetMapping("/example/example-form/{uid}")
    public ModelAndView exampleForm (
            @PathVariable(value = "uid") String uid,
            Model model
    ) {
        ModelAndView mv = new ModelAndView("example/exampleForm");

        examplePageService.selectExample(uid, model);

        return mv;
    }
}
