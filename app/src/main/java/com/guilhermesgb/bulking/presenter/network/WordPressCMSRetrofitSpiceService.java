package com.guilhermesgb.bulking.presenter.network;

import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class WordPressCMSRetrofitSpiceService extends RetrofitGsonSpiceService {

    private static final String BASE_URL = "http://wp.marcosnobrega.com.s101486.gridserver.com/";
    private static final Integer THREAD_COUNT = 5;

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(WordPressCMSRetrofitInterface.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Notification createDefaultNotification() {
        final Notification notification = super.createDefaultNotification();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            notification.priority = Notification.PRIORITY_MIN;
        }
        return notification;
    }

    @Override
    public int getThreadCount() {
        return THREAD_COUNT;
    }

}
