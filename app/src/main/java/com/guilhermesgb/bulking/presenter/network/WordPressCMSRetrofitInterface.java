package com.guilhermesgb.bulking.presenter.network;

import com.google.gson.JsonObject;

import retrofit.http.GET;
import retrofit.http.Path;

public interface WordPressCMSRetrofitInterface {

    @GET("/guide_items/page/{page}/?json=1")
    JsonObject retrieveGuideItems(@Path("page") Integer page);

}
