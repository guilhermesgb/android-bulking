package com.guilhermesgb.robospiceretrofit.model.storage;

import com.activeandroid.query.Select;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;

import java.io.IOException;
import java.util.LinkedList;
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

    public static List<GuideItem> parseAndSyncGuideItems(JsonObject response) throws IOException {
        List<GuideItem> parsedResponse = new LinkedList<>();
        JsonArray posts = response.getAsJsonArray("posts");
        for (JsonElement element : posts) {
            JsonObject post = element.getAsJsonObject();
            parsedResponse.add(new GuideItem(post));
        }
        return parsedResponse;
    }

}
