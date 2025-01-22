package com.skn.admin.file.controller;

import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.util.NTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController {

    private final FileInfoService fileService;

    @RequestMapping(value = "/file-form-inc")
    public String fileForm(HttpServletRequest request, Model model) {
        String parentTable = request.getParameter("parentTable");
        String parentUid = NTUtil.isNull(request.getParameter("parentUid"), "");
        String parentType = NTUtil.isNull(request.getParameter("parentType"), "");
        String description = Optional.ofNullable(request.getParameter("description")).orElse("");
        String viewType = NTUtil.isNull(request.getParameter("viewType"), "");

        if (!description.equals("")) {
            String[] lines = description.split("__");
            StringBuilder formattedDescription = new StringBuilder();

            for (String line : lines) {
                formattedDescription.append("* ").append(line).append("<br>");
            }
            model.addAttribute("description", formattedDescription.toString());
        }

        if(!parentUid.equals("")) {

            model.addAttribute("fileList", fileService.getFileInfoList(parentTable, parentUid, parentType));
        }
        model.addAttribute("viewType", viewType);
        model.addAttribute("parentUid", parentUid);
        model.addAttribute("imgFileExtensions", getImageExtensions());

        return "/common/inc/file-form-inc";

    }
    public List<String> getImageExtensions() {
        List<String> extensions = new ArrayList<>();
        extensions.add("jpg");
        extensions.add("jpeg");
        extensions.add("png");
        extensions.add("gif");
        extensions.add("bmp");
        extensions.add("svg");
        return extensions;
    }
}

