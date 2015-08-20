package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.squareup.picasso.Picasso;

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
        this.guideItemHotelCapacity.setText(guideItem.getCapacity());
    }

}
