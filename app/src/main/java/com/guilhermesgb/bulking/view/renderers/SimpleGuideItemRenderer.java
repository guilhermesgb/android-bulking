package com.guilhermesgb.bulking.view.renderers;

import android.content.Context;

import com.guilhermesgb.bulking.R;
import com.guilhermesgb.bulking.model.GuideItem;

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
