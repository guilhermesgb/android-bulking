package com.guilhermesgb.robospiceretrofit.network;

import com.google.gson.JsonObject;

import retrofit.http.GET;
import retrofit.http.Path;

public interface WordPressCMSRetrofitInterface {

    @GET("/guide_item/page/{page}/?json=1")
    JsonObject retrieveGuideItems(@Path("page") Integer page);

}
