package com.skn.admin.file.controller;

import com.skn.admin.config.annotation.Operation;
import com.skn.admin.config.api.apidto.APIDataResponse;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.dto.FileInit;
import com.skn.admin.file.mapper.FileInfoMapper;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.util.NTResult;
import com.skn.admin.util.NTUtil;


import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileApiController {

    private final FileInfoService fileInfoService;
    private final Environment env;
    private final FileInfoMapper fileMapper;

    @Operation("파일등록")
    @PostMapping("/file-upload")
    public @ResponseBody Map<String, Object> createFile(
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            HttpServletRequest request,
            @ModelAttribute FileInit fileInit
    ) throws IOException {
        NTResult ntResult = new NTResult();

        // 삭제할 거 있으면 삭제
        if (!NTUtil.isNull(fileInit.getParentUid()).equals("")) {
            fileInfoService.checkDeleteFIle(fileInit);
        }

        try {
            if(files != null) {
                List<MultipartFile> multipartFiles = Arrays.asList(files);
                if (multipartFiles.size() == 1) {
                    MultipartFile file = multipartFiles.get(0);

                    if(log.isDebugEnabled()){
                        log.debug("------------- file start -------------");
                        log.debug("name : "+file.getName());
                        log.debug("filename : "+file.getOriginalFilename());
                        log.debug("size : "+file.getSize());
                        log.debug("-------------- file end --------------\n");
                    }

                    if (!file.getOriginalFilename().equals("blob")) {
                        FileInfo fileInfo = fileInfoService.createFileInfo(fileInit, file, 0);
                        fileInfoService.saveFile(file, fileInfo);
                    }

                } else {
                    List<CompletableFuture<Void>> futures = multipartFiles.stream()
                            .map(file -> CompletableFuture.runAsync(() -> {
                                if(log.isDebugEnabled()){
                                    log.debug("------------- file start -------------");
                                    log.debug("name : "+file.getName());
                                    log.debug("filename : "+file.getOriginalFilename());
                                    log.debug("size : "+file.getSize());
                                    log.debug("-------------- file end --------------\n");
                                }

                                int index = multipartFiles.indexOf(file);
                                if (!file.getOriginalFilename().equals("blob")) {
                                    FileInfo fileInfo = fileInfoService.createFileInfo(fileInit, file, index);
                                    fileInfoService.saveFile(file, fileInfo);
                                }

                            }))
                            .collect(Collectors.toList());

                    futures.forEach(CompletableFuture::join);
                }
            }

            String[] uid = request.getParameterValues("Uid");
            String[] deleteYN = request.getParameterValues("DeleteYN");
            if(uid != null && uid.length != 0) {
                for(int i = 0; i < uid.length; i++){
                    log.info("::::::::: FILE INDEX::"+uid[i]);

                    if("Y".equals(deleteYN[i])) {
                        Map fileMap = new HashMap();
                        fileMap.put("uid", uid[i]);
                        fileMap.put("parentTable", request.getParameter("ParentTable"));
                        fileMap.put("parentUid", request.getParameter("ParentUid"));
                        fileInfoService.deleteFile(fileMap);
                        log.info(":::::::file delete:::::"+uid[i]);
                    }
                }
            }

            ntResult.setSuccess();
        } catch (Exception e) {
            ntResult.setFail();
            e.printStackTrace();
            throw new GeneralException(e);
        }
        return ntResult.getAsMap();
    }

    @Operation("파일 삭제")
    @PostMapping("/file-delete/delete/{uid}")
    public APIDataResponse<String> deleteFile(
            @PathVariable String uid
    ) throws IOException {
        if (!NTUtil.isNull(uid).equals("")) {
            fileInfoService.deleteFileApi(uid, "delete");
        }
        return APIDataResponse.of(Boolean.toString(true));
    }

    @Operation("파일 삭제")
    @PostMapping("/file-delete/delete")
    public APIDataResponse<String> deleteFileTest(@RequestBody Map<String, List<String>> params) {
        List<String> uid = params.get("Uid");
        try {
            if(uid != null && uid.size() != 0) {
                for(int i = 0; i < uid.size(); i++){
//                    log.info("::::::::: FILE INDEX::"+uid.get(i));
                    Map fileMap = new HashMap();
                    fileMap.put("uid", uid.get(i));
                    fileInfoService.deleteFile(fileMap);
//                    log.info(":::::::file delete:::::"+uid.get(i));
                }
            }
        } catch (Exception e) {
            System.out.println("첨부파일 삭제 오류");
            e.printStackTrace();
        }

        return APIDataResponse.of(Boolean.toString(true));
    }


    @Operation("파일 다운로드")
    @GetMapping("/file-download/{uid}")
    public ResponseEntity<?> downloadFile(@PathVariable String uid, HttpServletRequest request) throws IOException {
        if (!StringUtils.hasText(uid)) {
            return ResponseEntity.badRequest().body("UID EMPTY");
        }

        FileInfo fileInfo = fileInfoService.getFileInfo(uid);
        File downloadFile = new File(env.getProperty("skn.upload.path") + fileInfo.getSaveFilePath());

        String fileName = java.net.URLEncoder.encode(fileInfo.getFileName(), StandardCharsets.UTF_8).replaceAll("\\+","%20");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentLength(downloadFile.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @Operation("파일 설명 수정")
    @PostMapping(value={"/file-update/update/description/{uid}"})
    public ResponseEntity<?> updateAuditReport(@PathVariable String uid, @RequestBody Map<String, Object> formData) {
        List<String> descriptions = (List<String>) formData.get("description");
        List<String> fileUids = (List<String>) formData.get("fileUid");

        // descriptions와 fileUids가 존재하고 길이가 같은지 확인
        if (descriptions != null && fileUids != null && descriptions.size() == fileUids.size()) {
            // forEach를 사용하여 descriptions를 반복
            IntStream.range(0, descriptions.size()).forEach(i -> {
                String description = descriptions.get(i);
                String fileUid = fileUids.get(i);

                FileInfo fileInfo = new FileInfo();
                if ("business".equals(NTUtil.isNull(formData.get("type"), ""))) {
                    List<String> uids = (List<String>) formData.get("uid");
                    fileInfo.setParentUid(NTUtil.isNull(uids.get(i), ""));
                } else {
                    fileInfo.setParentUid(NTUtil.isNull(uid, ""));
                }
                fileInfo.setUid(fileUid);  // fileUid 설정
                fileInfo.setDescription(description);  // description 설정
                fileInfo.setType("description");

                // 파일 정보 업데이트
                fileMapper.updateFileInfo(fileInfo);
            });
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.badRequest().body("대체 텍스트 수정에 실패하였습니다.");
        }
    }
}
