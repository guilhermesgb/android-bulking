package com.guilhermesgb.bulking.view.renderers;

import android.content.Context;

import com.guilhermesgb.bulking.model.GuideItem;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.LinkedList;
import java.util.List;

public class GuideItemsRendererBuilder extends RendererBuilder<GuideItem> {

    public GuideItemsRendererBuilder(Context context) {
        setPrototypes(getPrototypes(context));
    }

    @Override
    protected Class getPrototypeClass(GuideItem guideItem) {
        final String category = guideItem.getCategory();
        if (category != null) {
            switch (category) {
                case "HOTELS":
                    return HotelGuideItemRenderer.class;
                case "TOURIST_ATTRACTION":
                case "FOOD_DRINK":
                case "NIGHT_LIFE":
                    return SchedulableVenueGuideItemRenderer.class;
                case "TOURS":
                case "SHOP":
                    return LeisureGuideItemRenderer.class;
                default:
                    return SimpleGuideItemRenderer.class;
            }
        }
        return SimpleGuideItemRenderer.class;
    }

    private List<Renderer<GuideItem>> getPrototypes(Context context) {
        List<Renderer<GuideItem>> prototypes = new LinkedList<>();
        prototypes.add(new SimpleGuideItemRenderer(context));
        prototypes.add(new SchedulableVenueGuideItemRenderer(context));
        prototypes.add(new HotelGuideItemRenderer(context));
        prototypes.add(new LeisureGuideItemRenderer(context));
        return prototypes;
    }

}
