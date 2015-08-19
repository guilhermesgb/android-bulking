package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class HotelGuideItemRenderer extends GuideItemRenderer {

    @Bind(R.id.guide_item_hotel_cover) ImageView guideItemHotelCover;
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
        renderCover(guideItem);
        renderCapacity(guideItem);
    }

    private void renderCover(GuideItem guideItem) {
        if (guideItem.getImageUrl() == null || guideItem.getImageUrl().isEmpty()) {
            guideItemHotelCover.setImageResource(R.drawable.cover_jfl_alarmes_backup);
            return;
        }
        Picasso.with(getContext())
                .load(guideItem.getImageUrl())
                .into(guideItemHotelCover);
    }

    private void renderCapacity(GuideItem guideItem) {
        this.guideItemHotelCapacity.setText(guideItem.getCapacity());
    }

}
