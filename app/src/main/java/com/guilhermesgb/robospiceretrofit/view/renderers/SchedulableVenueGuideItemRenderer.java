package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;

import butterknife.Bind;

public class SchedulableVenueGuideItemRenderer extends VenueGuideItemRenderer {

    @Bind(R.id.guide_item_schedulable_venue_schedule) TextView guideItemSchedulableVenueSchedule;

    public SchedulableVenueGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.guide_item_schedulable_venue;
    }

    @Override
    protected void continueRendering(GuideItem guideItem) {
        super.continueRendering(guideItem);
        renderSchedule(guideItem);
    }

    private void renderSchedule(GuideItem guideItem) {
        guideItemSchedulableVenueSchedule.setText(guideItem.getSchedule());
        guideItemSchedulableVenueSchedule.setSelected(true);
    }

}
