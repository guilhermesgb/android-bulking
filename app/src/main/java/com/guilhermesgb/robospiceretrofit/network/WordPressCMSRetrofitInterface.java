package com.guilhermesgb.robospiceretrofit.network;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

public interface WordPressCMSRetrofitInterface {

    @GET("/guide_item/page/{page}/?json=1")
    Response retrieveGuideItems(@Path("page") Integer page);

}
