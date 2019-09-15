package com.weixin.bean;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by Administrator on 2017/7/18.
 */
public class FileBean {

    private String filename;
    private String fileId;
    private String contentType;
    private Long size;
    private File saveFile;

    public FileBean(String filename, String contentType, Long size) {
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
    }

    public FileBean(MultipartFile file, String fileId) {
        this.filename = file.getOriginalFilename();
        this.fileId = fileId;
        this.contentType = file.getContentType();
        this.size = file.getSize();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public File getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(File saveFile) {
        this.saveFile = saveFile;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
