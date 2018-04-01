package com.yuan.igallery.presenter.photo;

import com.yuan.igallery.model.bean.LocalFile;
import com.yuan.igallery.presenter.BasePresenter;
import com.yuan.igallery.view.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by yuan on 2017/10/5.
 */

public interface PhotoTaskContract {

    interface Presenter extends BasePresenter{


    }

    interface View extends BaseView<Presenter>{
        void getFolders(Map<String, List<LocalFile>>  folders);
    }
}
