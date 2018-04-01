package com.yuan.igallery;

import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yuan.igallery.view.photo.fragment.GalleryPhotoFragment;

public class GalleryMainActivity extends AppCompatActivity {

    public RadioGroup bar_bottom;
    private RadioButton rb_photo,rb_memory,rb_album;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);
        initView();
        setListeners();
    }

    private void initView() {
        bar_bottom = (RadioGroup) findViewById(R.id.rg_bottom_bar);
        rb_photo = (RadioButton) findViewById(R.id.rb_photo);
        rb_memory = (RadioButton) findViewById(R.id.rb_memory);
        rb_album = (RadioButton) findViewById(R.id.rb_album);
        tv_title = (TextView) findViewById(R.id.tv_main_title);
        tv_title.setText(getApplicationContext().getString(R.string.gallery_bottom_button_photo));
        replaceFragment(new GalleryPhotoFragment());
        setTabState();
    }

    private void setListeners() {
        bar_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_photo:
                        tv_title.setText(getApplicationContext().getString(R.string.gallery_bottom_button_photo));

                        replaceFragment(new GalleryPhotoFragment());
                        break;
                    case R.id.rb_memory:
                        tv_title.setText(getApplicationContext().getString(R.string.gallery_bottom_button_memory));

                        break;
                    case R.id.rb_album:
                        tv_title.setText(getApplicationContext().getString(R.string.gallery_bottom_button_album));
                        break;
                }
                setTabState();
            }
        });

    }

    private void replaceFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.sub_content,fragment).commit();
    }

    private void setTabState() {
        rb_photo.setTextColor(ContextCompat.getColor(this,
                rb_photo.isChecked() ? R.color.color_bottom_blue:R.color.color_black));
        rb_memory.setTextColor(ContextCompat.getColor(this,
                rb_memory.isChecked() ? R.color.color_bottom_blue:R.color.color_black));
        rb_album.setTextColor(ContextCompat.getColor(this,
                rb_album.isChecked() ? R.color.color_bottom_blue:R.color.color_black));
    }



}
