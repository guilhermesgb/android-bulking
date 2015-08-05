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
        return R.layout.renderer_guide_item;
    }

    protected void setUpParticularitiesInView(View view) {}

}
