package com.guilhermesgb.bulking.model.storage.requests;

import com.google.gson.JsonObject;
import com.guilhermesgb.bulking.model.storage.GuideItemsProvider;
import com.octo.android.robospice.request.SpiceRequest;

public class SyncStoredGuideItemsRequest extends SpiceRequest<Void> {

    private final JsonObject source;

    public SyncStoredGuideItemsRequest(JsonObject source) {
        super(Void.class);
        this.source = source;
    }

    @Override
    public Void loadDataFromNetwork() throws Exception {
        GuideItemsProvider.parseAndSyncGuideItemsWithStorage(source);
        return null;
    }

}
