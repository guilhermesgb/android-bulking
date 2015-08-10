package com.guilhermesgb.robospiceretrofit.model.storage.requests;

import com.guilhermesgb.robospiceretrofit.model.storage.GuideItemsProvider;
import com.octo.android.robospice.request.SpiceRequest;

public class StoredGuideItemsCountRequest extends SpiceRequest<Integer> {

    public StoredGuideItemsCountRequest() {
        super(Integer.class);
    }

    @Override
    public Integer loadDataFromNetwork() throws Exception {
        return GuideItemsProvider.getStoredGuideItemsCount();
    }

}
