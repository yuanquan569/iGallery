package com.yuan.igallery.view.photo.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuan.igallery.R;
import com.yuan.igallery.model.bean.LocalFile;
import com.yuan.igallery.presenter.PhotoPresenter;
import com.yuan.igallery.presenter.photo.PhotoTaskContract;
import com.yuan.igallery.view.photo.adapter.PhotoAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by yuan on 2017/9/26.
 */

public class GalleryPhotoFragment extends Fragment implements PhotoTaskContract.View{

    private PhotoTaskContract.Presenter mPhotoPresenter;
    private PhotoAdapter photoAdapter;
    private TextView tv_loading;
    private RecyclerView rv_photo;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery_photo,null);
        initView(view);
        loadData();
        return view;
    }

    private void initView(View view) {
        mPhotoPresenter = new PhotoPresenter(this);
        photoAdapter = new PhotoAdapter(getActivity());
        tv_loading = (TextView) view.findViewById(R.id.tv_album_loading);
        rv_photo = (RecyclerView) view.findViewById(R.id.rv_list_photo);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return photoAdapter.getItemViewType(position) == 1? 1 : 4;
            }
        });
        rv_photo.setLayoutManager(gridLayoutManager);

        //设置Item增加、移除动画
        rv_photo.setItemAnimator(new DefaultItemAnimator());
       //添加分割线
        rv_photo.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));;
        rv_photo.setAdapter(photoAdapter);
    }

    private void loadData() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("提示");
        progressDialog.setMessage("加载中");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();;
        mPhotoPresenter.start();
    }

    @Override
    public void getFolders(Map<String, List<LocalFile>> folders) {
        tv_loading.setVisibility(View.GONE);
        rv_photo.setVisibility(View.VISIBLE);
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        photoAdapter.setFileList(folders.get("所有图片"));
    }
}
