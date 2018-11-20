package com.gpdi.mdata.sys.utils;


import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;


/**
 * Created by Administrator on 2017-04-19.
 */
public class FtpClientUtil {

    public static FtpClient connectFTP(String url, int port, String username, String password) {
        FtpClient ftp = null;
        try {
            SocketAddress addr = new InetSocketAddress(url, port);
            ftp = FtpClient.create();
            ftp.connect(addr);
            ftp.login(username, password.toCharArray());
            ftp.setBinaryType();
        } catch (FtpProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return ftp;
    }

    /**
     * 关闭连接
     * @author
     * @date
     */
    public void closeConnect() {
        FtpClient ftp = null;
        try {
            if(ftp.isConnected()){
                ftp.close();
            }
            System.out.println("disconnect success");
        } catch (IOException ex) {
            System.out.println("not disconnect");
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
}
