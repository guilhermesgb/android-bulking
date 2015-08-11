package com.guilhermesgb.robospiceretrofit.model.storage.requests;

import com.guilhermesgb.robospiceretrofit.view.renderers.GuideItemCollection;
import com.guilhermesgb.robospiceretrofit.model.storage.GuideItemsProvider;
import com.octo.android.robospice.request.SpiceRequest;

public class FetchStoredGuideItemsRequest extends SpiceRequest<GuideItemCollection> {

    public FetchStoredGuideItemsRequest() {
        super(GuideItemCollection.class);
    }

    @Override
    public GuideItemCollection loadDataFromNetwork() throws Exception {
        return new GuideItemCollection(GuideItemsProvider.retrieveStoredGuideItems());
    }

}
