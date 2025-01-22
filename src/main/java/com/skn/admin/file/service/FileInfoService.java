package com.skn.admin.file.service;

import com.skn.admin.config.api.constant.ErrorCode;
import com.skn.admin.config.api.exception.GeneralException;
import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.dto.FileInit;
import com.skn.admin.file.mapper.FileInfoMapper;
import com.skn.admin.util.NTResult;
import com.skn.admin.util.NTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;



/**
 * @author jinstech
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileInfoService {
    private final FileInfoMapper fileMapper;

    private final SqlSessionFactory sqlSessionFactory;
    private String allowedFileExtension = "pdf;docx;xlsx;pptx;jpg;jpeg;gif;png;bmp;apng;webp;mp4;ai;xlsm;svg;";
    private String imageExt = "jpg;jpeg;gif;bmp;png;webp;apng";
    private String docExt = "docx;xls;xlsx;ppt;pptx;pdf;txt;";

    // TODO :: PROPERTIES
    @Value("${skn.upload.path}")
    private String uploadPath;

    public String getFileType(String isSecure){
        if("Y".equals(isSecure))
            return "secure";
        return "public";
    }

    /************************************************************* CREATE FILE_INFO ************************************************/

    public FileInfo createFileInfo(FileInit fileInit, MultipartFile file, int i) {
        FileInfo fileInfo = new FileInfo();
        String uploadLocation = "";
        String saveFileName = "";

        if(fileInit.getDescription()!= null && fileInit.getDescription().size() != 0) {
            fileInfo.setDescription(NTUtil.isNull(fileInit.getDescription().get(i),""));
        }

        fileInfo.setFileSize(Long.toString(file.getSize()));
        fileInfo.setParentUid(fileInit.getParentUid());
        fileInfo.setParentTable(fileInit.getParentTable().get(i));
        fileInfo.setParentType(fileInit.getParentType().get(i));
        fileInfo.setUid(RandomStringUtils.randomAlphanumeric(16));
        fileInfo.setFileName(file.getOriginalFilename());
        String ext = NTUtil.getFileExt(fileInfo.getFileName()).toLowerCase();
        fileInfo.setFileExtension(ext);
        uploadLocation =  "/" + getFileType(fileInfo.getIsSecure()) + "/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid() + "/";;
        saveFileName = fileInfo.getUid() + "." + fileInfo.getFileExtension();

        if (fileInfo.getParentType().equals("THUMBNAIL")) {
            uploadLocation = uploadLocation.replaceAll("public", "thumbnail");
//            fileInfo.setFilePath("/upload" + uploadLocation + saveFileName);
            fileInfo.setFileType("image");
        } else if (fileInfo.getIsSecure().equals("Y")){
            uploadLocation = uploadLocation.replaceAll("public", "secure");
//            fileInfo.setFilePath("/upload" + uploadLocation + saveFileName);
            fileInfo.setFileType("image");
        } else {
//            fileInfo.setFilePath("/upload" + uploadLocation + saveFileName);
            fileInfo.setFileType("image");
        }

        if (fileInit.getSortOrder() != null) {
            fileInfo.setSortOrder(fileInit.getSortOrder().get(i));
        }

        String isSecureStr = NTUtil.isNull(fileInit.getIsSecure().get(i),"N");
        if ("".equals(isSecureStr)) {
            isSecureStr = "N";
        }
        fileInfo.setIsSecure(isSecureStr);

        return fileInfo;
    }


    /**************************************************************** INIT ****************************************************/

    public boolean initFileInfo(MultipartFile file, FileInfo fileInfo) {

        String extChk = NTUtil.getFileExt(NTUtil.isNull(file.getOriginalFilename(),"") ).toLowerCase();

        if ("".equals(fileInfo.getParentTable()) ) {
            throw new GeneralException(ErrorCode.VALIDATION_ERROR,
                    ErrorCode.VALIDATION_ERROR.getMessage("invalid parentTable"));
        }

        if ("".equals(fileInfo.getParentUid())) {
            throw new GeneralException(ErrorCode.VALIDATION_ERROR,
                    ErrorCode.VALIDATION_ERROR.getMessage("invalid parentUid"));
        }

        if(!allowedFileExtension.contains(extChk)) {
            throw new GeneralException(ErrorCode.VALIDATION_ERROR,
                    ErrorCode.VALIDATION_ERROR.getMessage("invalid fileName"));
        }

        return true;
    }






    /************************************************************* CREATE FILE ************************************************/

    public Map<String, String> saveFile(MultipartFile file, FileInfo fileInfo) {
        log.debug("saveFile START ----------------------------------------");
        NTResult ntResult = new NTResult();
        ntResult.setMsg("saveFile");

        try {
            log.debug("Original File Name : " + file.getOriginalFilename());


            String dir = uploadPath + "/" + getFileType(fileInfo.getIsSecure()) + "/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid() + "/";
            log.debug("Saved File Path : " + dir);
            log.debug("GET FILE UID : " + fileInfo.getUid());


            // 디렉토리 생성
            File fdir = new File(dir);
            if (!fdir.exists()) {
                fdir.mkdirs();
                fdir.setWritable(true); // 2016 12 09 hjkim
            }

            String saveFilePath = dir + fileInfo.getUid() + "." + fileInfo.getFileExtension();

            // 파일 생성
            File tFile = new File(saveFilePath);
            file.transferTo(tFile);
            tFile.setReadable(true);
            tFile.setWritable(true);


            fileMapper.insertFileInfo(fileInfo);
            ntResult.setSuccess();


            log.info("============================== Thumbnail ==========================");
            // TODO :: 여기서 PDF 파일의 경우 이미지 썸네일 생성, 각 종 이미지 파일들의 썸네일이 필요한 경우 파일 업로드 후 여기서 처리하도록.
            // PDF 썸네일 규칙. 저장된 파일명 + ".jpg" ex)45dADffggdafd.pdf.jpg
//            if (!fileInfo.getParentTable().equals("contract")) {
//                if ("pdf".equals(fileInfo.getFileExtension())) {
//                    this.createPdfThumbnail(fileInfo);
//                } else if ("jpg".equals(fileInfo.getFileExtension())) {
//                    this.createImgThumb(fileInfo);
//                } else if ("png".equals(fileInfo.getFileExtension())) {
//                    this.createImgThumb(fileInfo);
//                } else if ("ppt".equals(fileInfo.getFileExtension()) || "pptx".equals(fileInfo.getFileExtension())) {
//                    this.createPptThumb(fileInfo);
//                }
//            }

            // TODO :: 여기서 office 파일의 경우 pdf 생성, WEB 에서 파일을 봐야할 경우
            // content , brand 업로드 경우에만.
            // PDF 규칙. 저장된 파일명 + ".pdf" ex)45dADffggdafd.pdf.pdf


            log.info("=========== uploadTable ::: " + fileInfo.getParentTable().toLowerCase().replace("_","-") + "==========");
        } catch(IOException e) {
            //e.printStackTrace();
            log.debug("FilInfoService.saveFile IOException : " + e);
            ntResult.setMsg("catced exception");
        }
        return ntResult.getAsMap();
    }

    /************************************************************** DELETE *******************************************************/

    public boolean deleteFileApi(String uid, String type) {


        List<FileInfo> fileInfoList = fileMapper.selectFileInfoViewApi(uid);

        for (FileInfo fileInfo : fileInfoList) {
            String dir = uploadPath + "/" + getFileType(fileInfo.getIsSecure()) + "/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid() + "/";
            // pdf 변환파일
            String dir2 = uploadPath + "/pdf/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid() + "/";
            // 썸네일 생성파일
            String dir3 = uploadPath + "/thumbnail/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid() + "/";

            dir += fileInfo.getUid() + "." + fileInfo.getFileExtension();
            dir2 += fileInfo.getUid() + "." + fileInfo.getFileExtension() + ".pdf";
            dir3 += fileInfo.getUid() + "." + fileInfo.getFileExtension() + ".jpg";
            File t = new File(dir);
            File t2 = new File(dir2);
            File t3 = new File(dir3);

            if (t2.exists()) {
                boolean t2Br = t2.delete();
            }

            if (t3.exists()) {
                boolean t3Br = t3.delete();
            }
            boolean br = t.delete();
            fileMapper.deleteFileInfoApi(fileInfo);
            if (type.equals("delete")) {
                String dir1 = uploadPath + "/public/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid();
                File t1 = new File(dir1);
                log.debug("++++++++++++++DELETEFILE++++++++++111:::" + t1);
                NTUtil.deleteDirectory(t1);

                String dir2_ = uploadPath + "/secure/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid();
                File t2_ = new File(dir2_);
                log.debug("++++++++++++++DELETEFILE++++++++++222:::" + t2_);
                NTUtil.deleteDirectory(t2_);
                String dir3_ = uploadPath + "/thumbnail/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid();
                File t3_ = new File(dir3_);
                log.debug("++++++++++++++DELETEFILE++++++++++333:::" + t3_);
                NTUtil.deleteDirectory(t3_);
            }

        }
        return true;
    }

    public boolean deleteFile(Map fileMap)
    {
        log.debug("::::::::::::::::::::::::"+NTUtil.isEmpty((String)fileMap.get("parentUid")));
        log.debug("::::::::::::::::::::::::"+NTUtil.isEmpty((String)fileMap.get("parentTable")));

        if(!NTUtil.isEmpty((String)fileMap.get("uid")))
        {
            FileInfo fileInfo = fileMapper.selectFileInfoView(fileMap.get("uid").toString());

            String dir = uploadPath + "/" + getFileType(fileInfo.getIsSecure()) + "/" + fileInfo.getParentTable().toLowerCase().replace("_","-") + "/" + fileInfo.getParentUid() + "/";
            dir += fileInfo.getUid() + "." + fileInfo.getFileExtension();
            File t = new File(dir);
            boolean br = t.delete();
            fileMapper.deleteFileInfo(fileMap);
        } else if(!NTUtil.isEmpty((String)fileMap.get("parentUid")) && !NTUtil.isEmpty((String)fileMap.get("parentTable")) ) {
            String parentTable = (String) fileMap.get("parentTable");
            String dir1 = uploadPath + "/public/" + parentTable.toLowerCase().replace("_","-")+ "/" + fileMap.get("parentUid");
            File t1 = new File(dir1);
            log.debug("++++++++++++++DELETEFILE++++++++++111:::"+t1);
            NTUtil.deleteDirectory(t1);

            String dir2 = uploadPath + "/secure/" + parentTable.toLowerCase().replace("_","-")+ "/" + fileMap.get("parentUid");
            File t2 = new File(dir2);
            log.debug("++++++++++++++DELETEFILE++++++++++222:::"+t2);
            NTUtil.deleteDirectory(t2);
            fileMapper.deleteFileInfo(fileMap);
        } else {
            return false;
        }


        return true;
    }

    /*********************************************************************************************************************/

    /**
     * @param fileMap
     * @return
     * @throws IOException
     */
    public Map<String, String> moveFile(Map fileMap) throws IOException
    {
        log.debug(":::::::::::::::::::::START MOVE FILE :::::::::::::::::::::");

        NTResult ntResult = new NTResult();
        if(NTUtil.isEmpty((String)fileMap.get("parentUid")) || NTUtil.isEmpty((String)fileMap.get("parentTable")) || NTUtil.isEmpty((String)fileMap.get("tempKey"))) {
            ntResult.setMsg("잘못된 요청입니다.");
            return ntResult.getAsMap();
        }

        String source1 = uploadPath + "/" + getFileType((String) fileMap.get("isSecure")) + "/" + fileMap.get("parentTable") + "/" + fileMap.get("tempKey");
        String source2 = uploadPath + "/" + getFileType((String) fileMap.get("isSecure")) + "/" + fileMap.get("parentTable") + "/" + fileMap.get("tempKey");

        String destination1 = uploadPath +  "/" + getFileType((String) fileMap.get("isSecure")) + "/" + fileMap.get("parentTable") + "/" + fileMap.get("parentUid");
        String destination2 = uploadPath +  "/" + getFileType((String) fileMap.get("isSecure")) + "/" + fileMap.get("parentTable") + "/" + fileMap.get("parentUid");

        File s1 = new File(source1);
        File s2 = new File(source2);

        File d1 = new File(destination1);
        File d2 = new File(destination2);

        if(!d1.exists()){
            d1.mkdirs();
            d1.setWritable(true); // 2016 12 09 hjkim
        }



        if(!d2.exists()){
            d2.mkdirs();
            d2.setWritable(true); // 2016 12 09 hjkim
        }



        //s1.renameTo(d1);
        //s2.renameTo(d2);
        //FileUtils.moveDirectoryToDirectory(s1, d1, true);
        //FileUtils.moveDirectoryToDirectory(s1, d2, true);

        NTUtil.moveDirectory(s1, d1);
        NTUtil.moveDirectory(s2, d2);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setTempKey((String)fileMap.get("tempKey"));
        fileInfo.setParentUid((String)fileMap.get("parentUid"));
        fileInfo.setParentTable((String)fileMap.get("parentTable"));
        fileInfo.setDescription(null);
        fileMapper.updateFileInfo(fileInfo);

        NTUtil.deleteDirectory(s1);
        NTUtil.deleteDirectory(s2);

        ntResult.setSuccess();
        log.debug("::::::::::::::::::::: END MOVE FILE :::::::::::::::::::::");
        return ntResult.getAsMap();
    }

    /**
     * @param
     * @return
     * @throws IOException
     */
    public Map<String, String> moveFileByEditor(Map fileMap) throws IOException
    {
        log.debug(":::::::::::::::::::::START MOVE FILE :::::::::::::::::::::");

        NTResult ntResult = new NTResult();
        if(NTUtil.isEmpty((String)fileMap.get("parentUid")) || NTUtil.isEmpty((String)fileMap.get("parentTable")) )
        {
            ntResult.setMsg("잘못된 요청입니다.");
            return ntResult.getAsMap();
        }

        String source2 = uploadPath + "/" + fileMap.get("parentUid");

        String destination2 = uploadPath + "/" + fileMap.get("parentTable") + "/" + fileMap.get("parentUid") + "/editor";

        File s2 = new File(source2);
        File d2 = new File(destination2);

        if(!d2.exists()){
            d2.mkdirs();
            d2.setWritable(true); // 2016 12 09 hjkim
        }


        NTUtil.moveDirectory(s2, d2);

        NTUtil.deleteDirectory(s2);

        ntResult.setSuccess();
        log.debug("::::::::::::::::::::: END MOVE FILE :::::::::::::::::::::");
        return ntResult.getAsMap();
    }

    public String replaceWithEditorPath(Map fileMap, String contents){
        if(NTUtil.isEmpty((String)fileMap.get("parentUid")) || NTUtil.isEmpty((String)fileMap.get("parentTable")) || NTUtil.isEmpty((String)fileMap.get("tempKey")) )
        {
            return contents;
        }
        return contents.replaceAll("/"+fileMap.get("tempKey")+"/", "/"+fileMap.get("parentTable") + "/" + fileMap.get("parentUid") + "/editor/");
    }

    public void updateFileInfo(FileInfo fileInfo){
        this.fileMapper.updateFileInfo(fileInfo);
    }

    public FileInfo getFileInfo(String uid) {

        return fileMapper.selectFileInfoView(uid);
    }

    public FileInfo getFileInfoParentUid(String parentUid)
    {
        Map<String, String> fileMap = new HashMap();
        fileMap.put("parentUid", parentUid);

        FileInfo fileInfo = this.fileMapper.selectFileInfoViewParentUid(fileMap);

        return fileInfo;
    }

    public FileInfo getFileInfoParentUidAndType(String parentUid, String parentType)
    {
        Map<String, String> fileMap = new HashMap<>();
        fileMap.put("parentUid", parentUid);
        fileMap.put("parentType", parentType);

        return this.fileMapper.getFileInfoParentUidAndType(fileMap);
    }

    public List<FileInfo> getFileInfoListParentUid(String parentUid)
    {
        Map<String, String> fileMap = new HashMap();
        fileMap.put("parentUid", parentUid);

        return this.fileMapper.selectFileInfoListViewParentUid(fileMap);
    }

    public List<FileInfo> getFileInfoList(String parentTable, String parentUid, String parentType){
        Map<String, String> fileMap = new HashMap<String, String>();
        fileMap.put("parentTable", parentTable.toUpperCase());
        fileMap.put("parentUid", parentUid);
        fileMap.put("parentType", parentType);

        return fileMapper.selectFileInfoList(fileMap);
    }


    /*********************************************************************** Thumbnail Folder ***************************************************/

    // -------------------------- PDF 썸네일 Start
    private void createPdfThumbnail(FileInfo fileInfo) {
        String savedFilePath = null;
        String savedFileName = null;
        String thumbPath = "/thumbnail/";
        PDDocument document = null;
        PDFRenderer renderer = null;
        BufferedImage image = null;
        File file = null;
        File thumbnail = null;

//        savedFilePath = fileInfo.getFilePath();
        savedFileName = fileInfo.getSavedFileName();

        try {
            file = new File(uploadPath + "/" + savedFilePath.replaceAll("upload/thumbnail", "public"));
            thumbnail = new File(uploadPath + thumbPath + fileInfo.getFileDirectory() + savedFileName);

            if (!thumbnail.exists()) {
                thumbnail.mkdirs();
            }

            document = PDDocument.load(file);
            renderer = new PDFRenderer(document);
            image = renderer.renderImageWithDPI(0, 100, ImageType.RGB);
//			renderer.renderImage(0, (float)1);
            log.debug(uploadPath + thumbPath + fileInfo.getFileDirectory() + savedFileName + ".jpg");
            thumbnail = new File(uploadPath + thumbPath + fileInfo.getFileDirectory() + savedFileName + ".jpg");

            ImageIO.write(image, "jpg", thumbnail);

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(document != null){ try { document.close(); }catch(IOException e){ e.printStackTrace(); }}
        }
    }
    // -------------------------- PDF 썸네일 END

    // -------------------------- 이미지 리사이즈 Start
    public void createImgThumb(FileInfo fileInfo) throws IOException{

        String savedFilePath = null;
        String savedFileName = null;
        String thumbPath = "/thumbnail/";
        File file = null;
        File thumbnail = null;

//        savedFilePath = fileInfo.getFilePath();
        savedFileName = fileInfo.getSavedFileName();

        file = new File(uploadPath + "/" + savedFilePath.replaceAll("upload/thumbnail", "public"));
        thumbnail = new File(uploadPath + thumbPath + fileInfo.getFileDirectory() + savedFileName);

        if(!thumbnail.exists()){
            thumbnail.mkdirs();
        }

        int index = savedFileName.lastIndexOf(".");
        String ext = savedFileName.substring(index + 1); // 파일 확장자

        double ratio = 2; // 이미지 축소 비율

        try {
            BufferedImage oImage = ImageIO.read(file); // 원본이미지
            int tWidth = 256; //(int) (oImage.getWidth() / ratio); // 생성할 썸네일이미지의 너비
            int tHeight = 256; //(int) (oImage.getHeight() / ratio); // 생성할 썸네일이미지의 높이
            BufferedImage tImage = null;
            if(fileInfo.getFileExtension().equals("png")) {
                tImage =  new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_INT_ARGB); // 썸네일이미지
            } else {
                tImage =  new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR); // 썸네일이미지
            }
            Graphics2D graphic = tImage.createGraphics();
            Image image1 = oImage.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
            graphic.drawImage(image1, 0, 0, tWidth, tHeight, null);
            graphic.dispose(); // 리소스를 모두 해제

            ImageIO.write(tImage, ext, thumbnail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // -------------------------- 이미지 리사이즈 END

    // -------------------------- PPT 1페이지 썸네일 Start
    public void createPptThumb(FileInfo fileInfo) {

        String savedFilePath = null;
        String savedFileName = null;
        String thumbPath = "/thumbnail/";
        BufferedImage image = null;
        File file = null;
        File thumbnail = null;

//        savedFilePath = fileInfo.getFilePath();
        savedFileName = fileInfo.getSavedFileName();

        XMLSlideShow ppt = null;

        try {
            //썸네일 경로
            thumbnail = new File(uploadPath + thumbPath + fileInfo.getFileDirectory() + savedFileName);

            if(!thumbnail.exists()) {
                thumbnail.mkdirs();
            }

            // 원본파일
            file = new File(uploadPath + "/" + savedFilePath);
            ppt = new XMLSlideShow(new FileInputStream(file));

            Dimension pgSize = ppt.getPageSize();
            List<XSLFSlide> slide = ppt.getSlides();

            image = new BufferedImage(pgSize.width, pgSize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();

            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgSize.width, pgSize.height));

            slide.get(0).draw(graphics);

            //썸네일 저장
            thumbnail = new File(uploadPath + thumbPath + fileInfo.getFileDirectory() + savedFileName + ".jpg");

            ImageIO.write(image, "jpg", thumbnail);
            ppt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ppt != null){ try { ppt.close(); }catch(IOException e){ e.printStackTrace(); }}
        }
    }
    // -------------------------- PPT 1페이지 썸네일 End

    public Map<String, String> saveFile(Map map, String tempKey)
    {
        NTResult ntResult = new NTResult();

        log.info("::::::::::::::::::::: dropzone file :::::::::::::::::::::");
        log.info(String.valueOf(NTUtil.isNull(map.get("parentIndex")).equals("")));

        HashMap<String, String> uploadInfo = new HashMap<String, String>();
        uploadInfo.put("parentIndex", NTUtil.isNull(map.get("parentIndex")));
        uploadInfo.put("parentTable", NTUtil.isNull(map.get("parentTable")));
        uploadInfo.put("parentUid", NTUtil.isNull(map.get("parentUid")));
        uploadInfo.put("uid", tempKey);

        try {
            ntResult.setAll(this.moveFile(uploadInfo));
            if(ntResult.isFail()){
                ntResult.setMsg("FAIL : MOVE FILE");
                return ntResult.getAsMap();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("============= FAIL : MOVE FILE =============");
        }


        ntResult.setSuccess();
        return ntResult.getAsMap();
    }

    private static String getFileEncoding(String filePath) {
        String fileEncodingStr = "EUC-KR";

        try {
            FileInputStream fis = new FileInputStream(filePath);

            byte[] BOM = new byte[4];
            fis.read(BOM, 0, 4);

            if ((BOM[0] & 0xFF) == 0xEF && (BOM[1] & 0xFF) == 0xBB && (BOM[2] & 0xFF) == 0xBF) {
                fileEncodingStr = "UTF-8";
            } else if ((BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF) {
                fileEncodingStr = "UTF-16BE";
            } else if ((BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE) {
                fileEncodingStr = "UTF-16LE";
            } else if (
                    (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00
                            && (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF
            ) {
                fileEncodingStr = "UTF-32BE";
            } else if (
                    (BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE
                            && (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00
            ) {
                fileEncodingStr = "UTF-32LE";
            }

            fis.close();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("fileEncodingChk ERROR !! {}", e.getMessage());
            }
        } finally {

        }

        return fileEncodingStr;
    }

    @Transactional
    public void checkDeleteFIle(FileInit fileInit) {

        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
        // parentUid, parentTable, parentType(복합키) 파일이 1개만 들어간다는 가정 를 통해서 파일을 찾은 후 삭제

        String parentUid = fileInit.getParentUid();
        List<FileInfo> uid = new ArrayList<>();
        try {

            FileInfoMapper mapper = session.getMapper(FileInfoMapper.class);

            for (int i = 0; i <  fileInit.getParentTable().size(); i++) {
                String fileTable = fileInit.getParentTable().get(i);
                String parentType = fileInit.getParentType().get(i);

                FileInfo fileUid = mapper.selectFileUidByParentUidAndFileTableAndParentType(parentUid, fileTable, parentType);

                if (fileUid != null) {
                    uid.add(fileUid);
                }
            }
            session.flushStatements();

            for (FileInfo x : uid) {
                mapper.deleteFileInfoByUid(x.getUid());

                String dir1 = uploadPath + x.getSaveFilePath();
                File t1 = new File(dir1);
                t1.delete();
                log.debug("++++++++++++++DELETEFILE++++++++++111:::" + t1);

            }
            session.commit();



        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            throw e;        // 파일 삭제 실패시 상위 메소드에서 예외처리
        } finally {
            if (session != null) {
                session.close();
            }
        }

        for (FileInfo x : uid) {
            // file Delete
        }
    }
}

