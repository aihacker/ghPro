package com.weixin.bean.message;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class Video extends File {

    private String title;
    private String description;

    public Video() {
        super("video");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
