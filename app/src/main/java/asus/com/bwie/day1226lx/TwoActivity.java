    package asus.com.bwie.day1226lx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asus.com.bwie.day1226lx.bean.ShopBean;
import asus.com.bwie.day1226lx.presenter.IpresenterImpl;
import asus.com.bwie.day1226lx.view.IView;

    public class TwoActivity extends AppCompatActivity implements IView {

        private Banner pager;
        private TextView tTitle,tPrice;
        private IpresenterImpl iPersenter;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        init();
    }


    private void init() {
        pager = findViewById(R.id.pager);
        tTitle = findViewById(R.id.text_title);
        tPrice = findViewById(R.id.text_price);
        iPersenter = new IpresenterImpl(this);
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 0);

        pager.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        pager.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);

                return imageView;
            }
        });


        Map<String,String> map = new HashMap<>();
        map.put("pid",pid+"");
        iPersenter.startRequest(Apis.shopPath,map,ShopBean.class);

    }

    @Override
        public void OnSuccessData(Object data) {
            if (data instanceof ShopBean){
                ShopBean bean= (ShopBean) data;

            }

        }

        @Override
        public void OnFailData(Exception e) {
            iPersenter.detach();
        }
    }
