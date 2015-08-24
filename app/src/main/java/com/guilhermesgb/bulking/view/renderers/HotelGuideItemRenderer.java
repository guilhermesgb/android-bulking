package com.guilhermesgb.bulking.view.renderers;

import android.content.Context;
import android.widget.TextView;

import com.guilhermesgb.bulking.R;
import com.guilhermesgb.bulking.model.GuideItem;

import butterknife.Bind;

public class HotelGuideItemRenderer extends VenueGuideItemRenderer {

    @Bind(R.id.guide_item_hotel_capacity) TextView guideItemHotelCapacity;

    public HotelGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.guide_item_hotel;
    }

    @Override
    public void continueRendering(GuideItem guideItem) {
        super.continueRendering(guideItem);
        renderCapacity(guideItem);
    }

    private void renderCapacity(GuideItem guideItem) {
        guideItemHotelCapacity.setText(guideItem.getCapacity());
    }

}
