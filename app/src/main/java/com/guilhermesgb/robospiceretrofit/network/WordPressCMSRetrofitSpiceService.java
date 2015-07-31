package com.guilhermesgb.robospiceretrofit.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class WordPressCMSRetrofitSpiceService extends RetrofitGsonSpiceService {

    private static final String BASE_URL = "http://wp.marcosnobrega.com.s101486.gridserver.com/";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(WordPressCMSRetrofitInterface.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

}
