package com.guilhermesgb.bulking.view.renderers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.guilhermesgb.marqueeto.LabelledMarqueeEditText;
import com.guilhermesgb.bulking.R;
import com.guilhermesgb.bulking.model.GuideItem;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import butterknife.Bind;

public abstract class VenueGuideItemRenderer extends GuideItemRenderer {

    @Bind(R.id.guide_item_venue_phone) LabelledMarqueeEditText guideItemVenuePhone;
    @Bind(R.id.guide_item_venue_website) LabelledMarqueeEditText guideItemVenueWebsite;
    @Bind(R.id.guide_item_venue_email) LabelledMarqueeEditText guideItemVenueEmail;
    @Bind(R.id.guide_item_venue_address) LabelledMarqueeEditText guideItemVenueAddress;
    @Bind(R.id.guide_item_venue_cost) TextView guideItemVenueCost;

    public VenueGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected void continueRendering(GuideItem guideItem) {
        renderPhone(guideItem);
        renderWebsite(guideItem);
        renderEmail(guideItem);
        renderAddress(guideItem);
        renderCost(guideItem);
    }

    private void renderPhone(GuideItem guideItem) {
        final String phone = guideItem.getPhone();
        guideItemVenuePhone.setText(phone);
    }

    private void renderWebsite(GuideItem guideItem) {
        final String website = guideItem.getWebsite();
        guideItemVenueWebsite.setText(website);
    }

    private void renderEmail(GuideItem guideItem) {
        final String email = guideItem.getEmail();
        guideItemVenueEmail.setText(email);
    }

    private void renderAddress(GuideItem guideItem) {
        final String address = guideItem.getAddress();
        guideItemVenueAddress.setText(address);
    }

    private void renderCost(GuideItem guideItem) {
        guideItemVenueCost.setText(guideItem.getCost());
        guideItemVenueCost.setSelected(true);
    }

}
