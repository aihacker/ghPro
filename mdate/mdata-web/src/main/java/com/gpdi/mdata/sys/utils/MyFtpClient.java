package com.gpdi.mdata.sys.utils;


import com.gpdi.mdata.web.utils.MyFileUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.omg.CORBA.StringHolder;

import java.io.*;
import java.net.SocketException;

/**
 * 重写listFile方法是因为path需要转码， 有需要转码的都需要重写，或者先将路径转码在传进来
 */
public class MyFtpClient extends FTPClient {

    // FTP协议里面，规定文件名编码为iso-8859-1
    public static final String FTP_CHARSET = "ISO-8859-1";
    public static final String UTF8_CHARSET = "UTF-8";
    private String myCharset = "GBK";

    private MyFtpClient() {
    }

    public static MyFtpClient getMyFtpClient(String ftpHost, String ftpUserName, String ftpPassword, int ftpPort) {
        MyFtpClient myFtpClient = new MyFtpClient();
        try {
            myFtpClient.connect(ftpHost, ftpPort);//
            myFtpClient.login(ftpUserName, ftpPassword);//
//            System.out.println("getReplyCode : " + myFtpClient.getReplyCode() + "," + myFtpClient.getReplyString()
//                    + "," + "ftpClient.getReply()" + "," + myFtpClient.getCharsetName());
            if (!FTPReply.isPositiveCompletion(myFtpClient.getReplyCode())) {
                System.out.println("!FTPReply.isPositiveCompletion");
                myFtpClient.disconnect();
            } else {
                System.out.println("FTPReply.isPositiveCompletion");
                if (FTPReply.isPositiveCompletion(myFtpClient.sendCommand("OPTS UTF8", "ON"))) {
                    // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                    myFtpClient.myCharset = UTF8_CHARSET;
                }
                myFtpClient.setControlEncoding(myFtpClient.myCharset);
                myFtpClient.enterLocalPassiveMode();// 设置被动模式
                //System.out.println("charset = " + myFtpClient.myCharset);
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return myFtpClient;
    }


    @Override
    public FTPFile[] listFiles(String pathname) throws IOException {
        if (pathname == null) {
            return null;
        }
        //System.out.print(pathname + " : " + myCharset + "  --  ");
        pathname = new String(pathname.getBytes(myCharset), FTP_CHARSET);
        //System.out.println(pathname);
        return super.listFiles(pathname);
    }


    public boolean downloadFtpFile(String ftpPath, String localPath,
                                   String fileName) {
        //ftpClient = null;
        try {
            setControlEncoding(myCharset);
            setFileType(FTPClient.BINARY_FILE_TYPE);
            enterLocalPassiveMode();
            String ftpPathTemp = new String(ftpPath.getBytes(myCharset), FTP_CHARSET);
            boolean res = changeWorkingDirectory(ftpPathTemp);
            //System.out.println("---- ---- changeWorkingDirectory is " + res);
            //System.out.println(" ftpPath : " + ftpPathTemp);

            File localFile = new File(localPath + File.separatorChar + fileName);
            OutputStream os = new FileOutputStream(localFile);
            String filenameTemp = new String(fileName.getBytes(myCharset), FTP_CHARSET);
            res = retrieveFile(filenameTemp, os);
            //System.out.println("---- ---- retrieveFile is " + res);
            os.close();
            logout();
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Description: 向FTP服务器上传文件
     *
     * @param ftpPath  FTP服务器中文件所在路径 格式： ftptest/aa
     * @param fileName ftp文件名称
     * @param input    文件流
     * @return 成功返回true，否则返回false
     */
    public boolean uploadFile(String ftpPath,
                              String fileName, InputStream input) {
        boolean success = false;
        try {
            int reply;
            //ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            reply = getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                disconnect();
                return success;
            }
            setControlEncoding(myCharset); // 中文支持
            setFileType(FTPClient.BINARY_FILE_TYPE);
            enterLocalPassiveMode();
            String ftpPathTemp = new String(ftpPath.getBytes(myCharset), FTP_CHARSET);
            boolean flag = changeWorkingDirectory(ftpPathTemp);
            if (!flag) {
                String[] dir = ftpPathTemp.split("/");
                for (int i = 0; i < dir.length; i++) {
                    makeDirectory(dir[i]);
                    changeWorkingDirectory(dir[i]);
                }
            }
//            long tarFilesize = 0;
//            FTPFile[] flst = null;
            String filenameTemp = new String(fileName.getBytes(myCharset), FTP_CHARSET);
            storeFile(filenameTemp, input);
//            flst = listFiles(new String(fileName.getBytes(myCharset), FTP_CHARSET));
//            tarFilesize = flst[0].getSize();
//            System.out.println("tarFilesize:" + tarFilesize);
            input.close();
            logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isConnected()) {
                try {
                    disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }


    //上传文件
    public boolean upload(String localFile, String remotePath, String remoteFile) {
        InputStream input = null;
        boolean success = false;
        try {
            int reply;
            reply = getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                disconnect();
                return success;
            }
            setControlEncoding(myCharset); // 中文支持
            setFileType(FTPClient.BINARY_FILE_TYPE);
            enterLocalPassiveMode();
            File file = new File(localFile);
            if (!file.exists())
                throw new Exception("本地文件或目录不存在:" + localFile);
            long srcFilesize = file.length();
            long tarFilesize = 0;
            String ftpPathTemp = new String(remotePath.getBytes(myCharset), FTP_CHARSET);
            boolean flag = changeWorkingDirectory(ftpPathTemp);
            if (!flag) {
                String[] dir = ftpPathTemp.split("/");
                for (int i = 0; i < dir.length; i++) {
                    makeDirectory(dir[i]);
                    changeWorkingDirectory(dir[i]);
                }
            }
            FTPFile[] flst = null;
            input = new FileInputStream(file);
            if (storeFile(new String(remoteFile.getBytes(myCharset), FTP_CHARSET), input)) {
                flst = listFiles(new String(remoteFile.getBytes(myCharset), FTP_CHARSET));
                if (flst.length != 1) {
                    throw new Exception("上传失败");
                } else
                    tarFilesize = flst[0].getSize();
            }
            if (srcFilesize != tarFilesize) {
                throw new Exception("上传后文件大小不一致:" + srcFilesize + " -> " + tarFilesize);
            }
            logout();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (isConnected()) {
                    try { disconnect();
                    } catch (IOException ioe) { }
                }
            } catch (Exception e) { }

            try { if (input != null) input.close();
            } catch (Exception e) { }
        }
        return true;
    }

    public String getMyCharset() {
        return myCharset;
    }
}