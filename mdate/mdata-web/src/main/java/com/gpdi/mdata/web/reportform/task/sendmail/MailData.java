package com.gpdi.mdata.web.reportform.task.sendmail;

import java.util.Map;

/**
 * @author gan
 * @date 2018/4/16
 * @using
 */
public class MailData implements Cloneable {
    private String staffName;
    private Integer logId;
    private String sendAddress;
    private Map<String,String> attach;
    private String html;

    @Override
    public MailData clone() throws CloneNotSupportedException {
        MailData data = (MailData)super.clone();
        return data;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public Map<String, String> getAttach() {
        return attach;
    }

    public void setAttach(Map<String, String> attach) {
        this.attach = attach;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
