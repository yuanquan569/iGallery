package com.yuan.igallery.view.photo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuan.igallery.R;
import com.yuan.igallery.model.bean.GalleryRecyclerAdapterItem;
import com.yuan.igallery.model.bean.LocalFile;
import com.yuan.igallery.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yuan on 2017/9/26.
 */

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<LocalFile> fileList;
    private List<GalleryRecyclerAdapterItem> adapterItems = new ArrayList<>();

    public PhotoAdapter(Context context){
        this.context =context;

    }

    public void setFileList(List<LocalFile> list){
        this.fileList = list;
        getAdapterItems();
        notifyDataSetChanged();
    }

    private void getAdapterItems(){

        Collections.sort(fileList, new Comparator<LocalFile>() {
            @Override
            public int compare(LocalFile o1, LocalFile o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        GalleryRecyclerAdapterItem titleAdapterItem = new GalleryRecyclerAdapterItem(0,
                new GalleryRecyclerAdapterItem.TitleItem(fileList.get(0).getDate().substring(0,10)), null);

        adapterItems.add(titleAdapterItem);
        for (int i =0; i < fileList.size(); i++){
            LocalFile localFile = fileList.get(i);

            if (localFile.getDate().length() > 10 &&
                    !localFile.getDate().substring(0,10).equals(titleAdapterItem.titleItem.date)){
                adapterItems.add(titleAdapterItem = new GalleryRecyclerAdapterItem(0,
                        new GalleryRecyclerAdapterItem.TitleItem(localFile.getDate().substring(0,10)),null));
            }

            GalleryRecyclerAdapterItem contentAdapterItem = new GalleryRecyclerAdapterItem(1,
                    null,new GalleryRecyclerAdapterItem.ContentItem(localFile));

            adapterItems.add(contentAdapterItem);
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == 0){

            view = LayoutInflater.from(context).inflate(R.layout.item_photo_title, parent,false);
            return new TitleViewHolder(view);
        }else if (viewType == 1){
            view = LayoutInflater.from(context).inflate(R.layout.item_photo_content, parent,false);

            return new ContentViewHoldert(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TitleViewHolder){
            ((TitleViewHolder) holder).tv_date.setText(adapterItems.get(position).titleItem.date);
        }else if (holder instanceof ContentViewHoldert){
            Glide.with(context).load(new File(FileUtils.getRealFilePath(context,adapterItems.get(position).contentItem.localFile.getThumbnailUri())))
                    .into(((ContentViewHoldert) holder).iv_content);
        }

    }

    @Override
    public int getItemCount() {
        return adapterItems.size();
    }


    @Override
    public int getItemViewType(int position) {
        return adapterItems.get(position).type;
    }


    private class  TitleViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_date;
        public TitleViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_gallery_date);

        }
    }

    private class ContentViewHoldert extends RecyclerView.ViewHolder{

        private ImageView iv_content;
        public ContentViewHoldert(View itemView) {
            super(itemView);
            iv_content = (ImageView) itemView.findViewById(R.id.iv_gallery_content);
        }
    }
}
