package asus.com.bwie.day1226lx.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetUtils {
    private static volatile InternetUtils instance;
    private Context context;

    private InternetUtils(Context context) {
        this.context = context;
    }

    public static InternetUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (InternetUtils.class) {
                if (instance == null) {
                    instance = new InternetUtils(context);
                }
            }
        }
        return instance;
    }

    public int getNetype() {
        int neType = -1;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return neType;
        }
        int phoneType = networkInfo.getType();
        if (phoneType == ConnectivityManager.TYPE_MOBILE) {
            neType = 2;
        } else if (phoneType == ConnectivityManager.TYPE_WIFI) {
            neType = 1;
        }
        return neType;
    }
    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        NetworkInfo networkinfo = manager.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }

        return true;
    }



}
