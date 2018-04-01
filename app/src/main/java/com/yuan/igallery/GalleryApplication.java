package com.yuan.igallery;

import android.app.Application;

/**
 * Created by yuan on 2017/10/5.
 */

public class GalleryApplication extends Application{

    private static GalleryApplication galleryApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        galleryApplication = this;
    }

    public static GalleryApplication getInstance(){
        return galleryApplication;
    }
}
