package com.guilhermesgb.robospiceretrofit.storage.requests;

import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.model.GuideItemCollection;
import com.octo.android.robospice.request.SpiceRequest;

public class StoredGuideItemsRequest extends SpiceRequest<GuideItemCollection> {

    public StoredGuideItemsRequest() {
        super(GuideItemCollection.class);
    }

    @Override
    public GuideItemCollection loadDataFromNetwork() throws Exception {
        return new GuideItemCollection(GuideItem.retrieveStoredGuideItems());
    }

}
