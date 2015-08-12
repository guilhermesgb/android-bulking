package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;

public class SimpleGuideItemRenderer extends GuideItemRenderer {

    public SimpleGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.guide_item_simple;
    }

    @Override
    public void continueRendering(GuideItem guideItem) {}

}
