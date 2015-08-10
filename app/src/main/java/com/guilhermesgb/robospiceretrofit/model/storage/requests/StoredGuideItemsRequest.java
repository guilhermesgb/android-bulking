package com.guilhermesgb.robospiceretrofit.model.storage.requests;

import com.guilhermesgb.robospiceretrofit.view.GuideItemCollection;
import com.guilhermesgb.robospiceretrofit.model.storage.GuideItemsProvider;
import com.octo.android.robospice.request.SpiceRequest;

public class StoredGuideItemsRequest extends SpiceRequest<GuideItemCollection> {

    public StoredGuideItemsRequest() {
        super(GuideItemCollection.class);
    }

    @Override
    public GuideItemCollection loadDataFromNetwork() throws Exception {
        return new GuideItemCollection(GuideItemsProvider.retrieveStoredGuideItems());
    }

}
