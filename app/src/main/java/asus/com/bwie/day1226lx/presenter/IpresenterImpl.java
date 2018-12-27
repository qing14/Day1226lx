package asus.com.bwie.day1226lx.presenter;

import java.util.Map;

import asus.com.bwie.day1226lx.callback.MyCallBack;
import asus.com.bwie.day1226lx.model.ImodelImpl;
import asus.com.bwie.day1226lx.view.IView;

public class IpresenterImpl implements Ipresenter{

    private IView mIView;
    private ImodelImpl imodel;

    public IpresenterImpl(IView mIView) {
        this.mIView = mIView;
        this.imodel = new ImodelImpl();
    }

    @Override
    public void startRequest(String urlData, Map<String, String> map, Class clazz) {
        imodel.startRequestData(urlData, map, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                mIView.OnSuccessData(data);
            }

            @Override
            public void OnFail(Exception e) {
                mIView.OnFailData(e);
            }
        });
    }
    public void detach(){
        imodel=null;
        mIView=null;
    }
}
