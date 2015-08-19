package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;

public class VenueGuideItemRenderer extends GuideItemRenderer {

    public VenueGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.guide_item_venue;
    }

    @Override
    protected void continueRendering(GuideItem guideItem) {

    }

}
