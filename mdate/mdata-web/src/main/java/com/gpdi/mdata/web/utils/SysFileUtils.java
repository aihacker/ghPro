package com.gpdi.mdata.web.utils;

import pub.web.ServletUtils;

/**
 * Created by zzl on 2015/12/30.
 */
public class SysFileUtils {

    public static String replaceUrlToPlaceholder(String html) {
        if(html == null) {
            return null;
        }
        String home = ServletUtils.getRequest().getContextPath();
        String prolog = "\"" + home + "/common/file/get.do?id=";

        StringBuilder sbHtml = new StringBuilder();
        int pos = 0;
        while(true) {
            int pos1 = html.indexOf(prolog, pos);
            if(pos1 == -1) {
                sbHtml.append(html.substring(pos));
                break;
            }
            int pos2 = html.indexOf('"', pos1 + 1);
            String fileId = html.substring(pos1 + prolog.length(), pos2);
            sbHtml.append(html.substring(pos, pos1));
            sbHtml.append("\"@(FILE-ID=" + fileId + ")\"");
            pos = pos2 + 1;
        }
        String html2 = sbHtml.toString();
        return html2;
    }

    public static String replacePlaceholderToUrl(String html) {
        if(html == null) {
            return null;
        }
        String home = ServletUtils.getRequest().getContextPath();
        String fileUrl = home + "/common/file/get.do?id=";

        String prolog = "@(FILE-ID=";

        StringBuilder sbHtml = new StringBuilder();
        int pos = 0;
        while(true) {
            int pos1 = html.indexOf(prolog, pos);
            if(pos1 == -1) {
                sbHtml.append(html.substring(pos));
                break;
            }
            int pos2 = html.indexOf(')', pos1);
            String fileId = html.substring(pos1 + prolog.length(), pos2);
            sbHtml.append(html.substring(pos, pos1));
            sbHtml.append(fileUrl + fileId);
            pos = pos2 + 1;
        }
        String html2 = sbHtml.toString();
        return html2;
    }
}
