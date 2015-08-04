package com.guilhermesgb.robospiceretrofit.renderers;

import android.content.Context;
import android.view.View;

import com.guilhermesgb.robospiceretrofit.R;

public class SimpleGuideItemRenderer extends GuideItemRenderer {

    public SimpleGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.guide_item_renderer;
    }

    protected void setUpParticularitiesInView(View view) {}

}
