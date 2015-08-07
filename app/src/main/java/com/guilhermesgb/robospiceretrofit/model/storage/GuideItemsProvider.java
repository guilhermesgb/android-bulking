package com.guilhermesgb.robospiceretrofit.model.storage;

import com.activeandroid.query.Select;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;

import java.io.IOException;
import java.util.List;

public class GuideItemsProvider {

    public static GuideItem retrieveStoredGuideItem(Integer guideItemId) {
        return new Select().from(GuideItem.class)
                .where("guideItemId = ?", guideItemId).executeSingle();
    }

    public static List<GuideItem> retrieveStoredGuideItems() {
        return new Select().all().from(GuideItem.class).execute();
    }

    public static int getStoredGuideItemsCount() {
        return new Select().all().from(GuideItem.class).count();
    }

    public static void parseAndSyncGuideItemsWithStorage(JsonObject response) throws IOException {
        JsonArray posts = response.getAsJsonArray("posts");
        for (JsonElement element : posts) {
            new GuideItem(element.getAsJsonObject());
        }
    }

}
