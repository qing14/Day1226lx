package asus.com.bwie.day1226lx;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import asus.com.bwie.day1226lx.bean.GreenShop;
import asus.com.bwie.day1226lx.bean.ShopBean;
import asus.com.bwie.day1226lx.dao.DaoMaster;
import asus.com.bwie.day1226lx.dao.DaoSession;
import asus.com.bwie.day1226lx.dao.GreenShopDao;
import asus.com.bwie.day1226lx.presenter.IpresenterImpl;
import asus.com.bwie.day1226lx.utils.InternetUtils;
import asus.com.bwie.day1226lx.view.IView;

public class MainActivity extends AppCompatActivity implements IView {

    private IpresenterImpl ipresenter;
    private int num = 2;
    private RecyclerView recyclerView;
    private int mpage = 1;
    private ShopAdapter shopAdapter;
    private ShopBean shopBean;

    private List<GreenShop> list;
    private DaoSession daoSession;
    private GreenShopDao greenShopDao;
    private ShopBean.DataBean dataBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipresenter = new IpresenterImpl(this);
        recyclerView = findViewById(R.id.recycleView);
        initDB();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, num);
        recyclerView.setLayoutManager(gridLayoutManager);
        shopAdapter = new ShopAdapter(this);
        recyclerView.setAdapter(shopAdapter);
        list = new ArrayList<>();
        requestData();


        shopAdapter.setOnLongClickListenter(new ShopAdapter.LongClickListenter() {
            @Override
            public void onLongClick(int position) {
                shopAdapter.delList(position);
                shopAdapter.notifyDataSetChanged();
            }
        });
        shopAdapter.setOnClickListenter(new ShopAdapter.ClickListenter() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(MainActivity.this,TwoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initDB() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"shop.dp",null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster =new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
        greenShopDao = daoSession.getGreenShopDao();
    }

    private void requestData() {

        boolean isNetwork = InternetUtils.isNetworkAvailable(this);
        if (isNetwork){
            Map<String, String> map = new HashMap<>();
            map.clear();
            map.put("keywords", "手机");
            map.put("page", mpage + "");
            ipresenter.startRequest(Apis.shopPath, map, ShopBean.class);
        }else {
            Map<String, String> map = new HashMap<>();
            map.clear();
            map.put("keywords", "手机");
            map.put("page", mpage + "");
            ipresenter.startRequest(Apis.shopPath, map, ShopBean.class);
            List<ShopBean.DataBean> list1 = shopBean.getData();
            List<GreenShop> list=greenShopDao.queryBuilder().list();
            for (int i=0;i<list.size();i++){
                dataBean = new ShopBean.DataBean();
                dataBean.setPid((int) list.get(i).getPid());
                dataBean.setTitle(list.get(i).getTitle());
                dataBean.setImages(list.get(i).getImages());
                dataBean.setPrice(list.get(i).getPrice());

                list1.add(dataBean);
            }
            shopAdapter.setList(list1);
        }




    }

    @Override
    public void OnSuccessData(Object data) {

        if (data instanceof ShopBean) {
            shopBean = (ShopBean) data;
            List<ShopBean.DataBean> beanData = shopBean.getData();
            shopAdapter.setList(shopBean.getData());
            for (int i=0;i<beanData.size();i++){
                GreenShop greenShop=new GreenShop();
                int pid = beanData.get(i).getPid();
                greenShop.setPid(pid);
                greenShop.setImages(beanData.get(i).getImages());
                greenShop.setTitle(beanData.get(i).getTitle());
                greenShop.setPrice(beanData.get(i).getPrice());
                greenShopDao.insertOrReplace(greenShop);

            }
        }
    }

    @Override
    public void OnFailData(Exception e) {

    }
}
