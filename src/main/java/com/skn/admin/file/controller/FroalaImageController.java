package com.skn.admin.file.controller;

import com.skn.admin.util.NTUtil;
import com.skn.froala.editor.Image;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/froalaeditor")
@MultipartConfig
public class FroalaImageController {
    @Value("${skn.upload.path}")
    public static String UPLOAD_IMAGE_PATH;

    @Value("${skn.upload.path}")
    private void setValue(String uploadPath){
        UPLOAD_IMAGE_PATH = uploadPath+ "/public";
    }

    public String getStaticPath(){
        return UPLOAD_IMAGE_PATH; // spring.profiles.active is default
    }

    @Value("${skn.upload.url}")
    public static String UPLOAD_IMAGE_URL;

    @Value("${skn.upload.url}")
    private void setValueUrl(String uploadUrl){
        UPLOAD_IMAGE_URL = uploadUrl+ "/public";
    }

    public String getStaticUrl(){
        return UPLOAD_IMAGE_URL; // spring.profiles.active is default
    }

    //public static final String UPLOAD_IMAGE_PATH = uploadPath + "/image";
    //public static final String UPLOAD_IMAGE_URL = getStaticHello();

    @RequestMapping(value={"/upload/Image"})
    public ModelAndView uploadImage(HttpServletRequest request) {

        String uploadPath = getStaticPath();

        String subPath = NTUtil.isNull(request.getParameter("editorUploadPath"), "") + "/";
        if(!subPath.equals(""))
            uploadPath += subPath;

        Map<Object, Object> responseData;
        try {
            responseData = Image.upload(request, uploadPath); // Use default
            String link = (String) responseData.get("link");
            link =  link.replace(getStaticPath(), getStaticUrl());
            responseData.put("link", link);
            // image
            // validation.
        } catch (Exception e) {
            e.printStackTrace();
            responseData = new HashMap<Object, Object>();
            responseData.put("error", e.toString());
        }

        Map<String, Object> returnMap = new HashMap();
        for(Object key : responseData.keySet()) {
            returnMap.put(key.toString(), responseData.get(key));
        }

        return new ModelAndView("jsonView", returnMap);
    }
}
