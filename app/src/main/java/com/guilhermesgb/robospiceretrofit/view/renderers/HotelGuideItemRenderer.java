package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;

import butterknife.Bind;

public class HotelGuideItemRenderer extends GuideItemRenderer {

    @Bind(R.id.guide_item_hotel_capacity) TextView guideItemHotelCapacity;

    public HotelGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_guide_item_hotel;
    }

    @Override
    public void continueRendering(GuideItem guideItem) {
        renderCapacity(guideItem);
    }

    private void renderCapacity(GuideItem guideItem) {
        this.guideItemHotelCapacity.setText(guideItem.getCapacity());
    }

}
