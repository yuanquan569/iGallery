package com.yuan.igallery.model.bean;

import java.io.Serializable;

/**
 * Created by yuan on 2018/3/28.
 */

public class GalleryRecyclerAdapterItem {

    public int type;
    public TitleItem titleItem;
    public ContentItem contentItem;

    public GalleryRecyclerAdapterItem(int type, TitleItem titleItem, ContentItem contentItem){

        this.type = type;
        this.titleItem = titleItem;
        this.contentItem = contentItem;
    }


    public static class TitleItem implements Serializable{
        public String date;

        public  TitleItem(String date){
            this.date = date;

        }

    }

    public static class ContentItem implements Serializable{

        public LocalFile localFile;
        public ContentItem(LocalFile localFile){
            this.localFile = localFile;
        }
    }



}
