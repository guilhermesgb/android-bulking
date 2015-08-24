package com.guilhermesgb.bulking.model.storage.requests;

import com.guilhermesgb.bulking.view.renderers.GuideItemCollection;
import com.guilhermesgb.bulking.model.storage.GuideItemsProvider;
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
