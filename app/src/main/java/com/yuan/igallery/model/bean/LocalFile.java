package com.yuan.igallery.model.bean;

/**
 * Created by yuan on 2017/10/6.
 */

public class LocalFile {

    private String originalUri;//原图URI
    private String thumbnailUri;//缩略图URI
    private int orientation;//图片旋转角度
    private String date;

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public String getOriginalUri() {
        return originalUri;
    }

    public void setOriginalUri(String originalUri) {
        this.originalUri = originalUri;
    }


    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int exifOrientation) {
        orientation =  exifOrientation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
