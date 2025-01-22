package com.skn.admin.file.service;

import com.skn.admin.config.api.exception.GeneralException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class UnzipService {

//    public void unzipFile(String zipFilePath, String destDir) throws IOException {
//        File dir = new File(destDir);
//        boolean indexFileFound = false;
//
//        if (!dir.exists()) dir.mkdirs();
//        byte[] buffer = new byte[1024];
//        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
//            ZipEntry zipEntry = zis.getNextEntry();
//            if (zipEntry != null) {
//                String topDirectoryName = zipEntry.getName().indexOf('/') != -1 ? zipEntry.getName().substring(0, zipEntry.getName().indexOf('/')) : "";
//
//                while (zipEntry != null) {
//                    String fileName = zipEntry.getName();
//
//                    if (fileName.startsWith(topDirectoryName + "/")) {
//                        fileName = fileName.substring(topDirectoryName.length() + 1);
//                    }
//
//                    if (fileName.isEmpty()) {
//                        zipEntry = zis.getNextEntry();
//                        continue;
//                    }
//
//                    File newFile = new File(destDir + File.separator + fileName);
//
//                    if (zipEntry.isDirectory()) {
//                        newFile.mkdirs();
//                    } else {
//                        String actualFileName = fileName.substring(fileName.lastIndexOf('/') + 1);
//                        if (actualFileName.startsWith("index")) {
//                            indexFileFound = true;
//                        }
//
//                        File parent = newFile.getParentFile();
//                        if (!parent.exists()) {
//                            parent.mkdirs();
//                        }
//                        try (FileOutputStream fos = new FileOutputStream(newFile)) {
//                            int len;
//                            while ((len = zis.read(buffer)) > 0) {
//                                fos.write(buffer, 0, len);
//                            }
//                        }
//                    }
//                    zipEntry = zis.getNextEntry();
//                }
//            }
//            zis.closeEntry();
//
//            if (!indexFileFound) {
//                throw new FileNotFoundException("Index file not found in the zip.");
//            }
//        }
//    }

    public void unzipFile(String zipFilePath, String destDir) throws IOException {
        File dir = new File(destDir);
        boolean indexFileFound = false;

        if (!dir.exists()) dir.mkdirs();
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();

                File newFile = new File(destDir, fileName); // 직접 파일 이름을 사용

                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    if (fileName.endsWith("index.html")) {
                        indexFileFound = true;
                    }

                    File parent = newFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();

            if (!indexFileFound) {
                throw new GeneralException("index.html 파일이 존재하지 않습니다.");
            }
        } catch (IOException e) {
            throw new GeneralException("ZIP 파일 구조를 다시 확인해주세요.");
        }
    }


}
