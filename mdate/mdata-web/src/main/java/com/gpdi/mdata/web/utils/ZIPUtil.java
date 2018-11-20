package com.gpdi.mdata.web.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.StringHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ZIPUtil {


    public static boolean Zip(String src, String dest, String pwd, StringHolder destHolder) throws Exception {
        boolean bRet = false;
        destHolder.value = "";
        File srcFile = new File(src);
        dest = MyFileUtil.forceDestZipPath(srcFile, dest);
        File destFile = new File(dest);
        if (destFile.exists()) destFile.delete();

        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // 压缩方式
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL); // 压缩级别

        if (!StringUtils.isEmpty(pwd)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD); // 加密方式
            parameters.setPassword(pwd.toCharArray());
        }

        ZipFile zipFile = new ZipFile(dest);
        zipFile.setFileNameCharset("GBK"); //默认为UTF-8
        zipFile.createZipFile(srcFile, parameters);
        bRet = true;
        destHolder.value = dest;

        return bRet;
    }

    public static void UnZip(String localDir, String localFileName, String pwd,
                             LinkedList<String> listFiles) throws Exception {
        try {
            ZipFile zFile = new ZipFile(localDir + "/" + localFileName);
            zFile.setFileNameCharset("GBK");
            if (!zFile.isValidZipFile()) {
                throw new Exception("压缩文件不合法,可能被损坏.");
            }
            String sDest = localDir + "/" + localFileName;
            sDest = MyFileUtil.forceCreateDir(sDest);
            File fDest = new File(sDest);

            if (zFile.isEncrypted()) {
                zFile.setPassword(pwd);
            }
            zFile.extractAll(sDest);

            @SuppressWarnings("unchecked")
            List<FileHeader> headerList = zFile.getFileHeaders();
            List<File> extractedFileList = new ArrayList<File>();
            for (FileHeader fileHeader : headerList) {
                if (!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(fDest, fileHeader
                            .getFileName()));
                }
            }
            for (File f : extractedFileList)
                listFiles.add(f.getCanonicalPath());
            if (listFiles.isEmpty())
                throw new Exception("压缩包中没有文件.");
        } catch (ZipException e) {
            throw new Exception(e.getMessage());
        }

    }

}
