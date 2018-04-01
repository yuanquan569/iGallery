package com.yuan.igallery.presenter;

import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.provider.MediaStore;

import com.yuan.igallery.GalleryApplication;
import com.yuan.igallery.model.bean.LocalFile;
import com.yuan.igallery.presenter.photo.PhotoTaskContract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuan on 2017/10/5.
 */

public class PhotoPresenter implements PhotoTaskContract.Presenter{

    private PhotoTaskContract.View mPhotoView;
    final List<LocalFile> paths = new ArrayList<>();
    Map<String, List<LocalFile>> folders = new HashMap<>();

    //大图遍历字段
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.ORIENTATION
    };

    //小图遍历字段
    private static final String[] THUMBNAIL_STORE_IMAGE = {
            MediaStore.Images.Thumbnails._ID,
            MediaStore.Images.Thumbnails.DATA
    };

    public PhotoPresenter(PhotoTaskContract.View view){
        this.mPhotoView = view;

    }

    @Override
    public void start() {

        Cursor cursor = GalleryApplication.getInstance().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                STORE_IMAGES,
                null,
                null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC");

        if (cursor == null) return;
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String path = cursor.getString(1);
            File file = new File(path);


            if(file.exists()){
                String thumbUri = getThumbnail(id,path);  //小图URI
                 String uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                        .appendPath(Integer.toString(id)).build().toString();

                if(StringUtils.isEmpty(uri)) continue;

                if(StringUtils.isEmpty(thumbUri)) thumbUri = uri;

                String folder = file.getParentFile().getName();

                LocalFile locaLFile = new LocalFile();
                locaLFile.setOriginalUri(uri);
                locaLFile.setThumbnailUri(thumbUri);
                locaLFile.setDate(getFileName(file));
                int degree = cursor.getInt(2);
                if(degree != 0){
                    degree = degree +100;
                }
                locaLFile.setOrientation(360-degree);

                paths.add(locaLFile);
                 if (folders.containsKey(folder)){
                  folders.get(folder).add(locaLFile);
                }else {
                    List<LocalFile> files = new ArrayList<>();
                    files.add(locaLFile);
                     folders.put(folder,files);
                }
            }
        }
        folders.put("所有图片", paths);
        cursor.close();

        mPhotoView.getFolders(folders);
    }

    private String getThumbnail(int id, String path){
        Cursor cursor = GalleryApplication.getInstance().getContentResolver().query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                THUMBNAIL_STORE_IMAGE,
                MediaStore.Images.Thumbnails.IMAGE_ID + " = ?",
                new String[]{id + ""},
                null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            int thumbId = cursor.getInt(0);
            String uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(Integer.toString(thumbId)).build().toString();
            cursor.close();
            return uri;
        }

        cursor.close();
        return null;
    }

    private String getFileName(File file){
        String date = null;
        try {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
             date = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (date == null) date = "1995:03:13 22:38:20";

        return date;
    }

}
