package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import butterknife.Bind;

public abstract class VenueGuideItemRenderer extends GuideItemRenderer {

    @Bind(R.id.guide_item_venue_phone) EditText guideItemVenuePhone;
    @Bind(R.id.guide_item_venue_phone_wrapper) TextInputLayout guideItemVenuePhoneWrapper;
    final IconDrawable phoneIcon = new IconDrawable(getContext(),
            MaterialIcons.md_local_phone).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    @Bind(R.id.guide_item_venue_website) EditText guideItemVenueWebsite;
    @Bind(R.id.guide_item_venue_website_wrapper) TextInputLayout guideItemVenueWebsiteWrapper;
    @Bind(R.id.guide_item_venue_website_marquee) TextView guideItemVenueWebsiteMarquee;
    final IconDrawable websiteIcon = new IconDrawable(getContext(),
            MaterialIcons.md_language).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    @Bind(R.id.guide_item_venue_email) EditText guideItemVenueEmail;
    @Bind(R.id.guide_item_venue_email_wrapper) TextInputLayout guideItemVenueEmailWrapper;
    final IconDrawable emailIcon = new IconDrawable(getContext(),
            MaterialIcons.md_local_post_office).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    @Bind(R.id.guide_item_venue_address) EditText guideItemVenueAddress;
    @Bind(R.id.guide_item_venue_address_wrapper) TextInputLayout guideItemVenueAddressWrapper;
    final IconDrawable addressIcon = new IconDrawable(getContext(),
            MaterialIcons.md_location_on).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
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
        guideItemVenuePhone.setCompoundDrawablesWithIntrinsicBounds(null, null, phoneIcon, null);
        final String phone = guideItem.getPhone();
        if (phone == null || phone.isEmpty()) {
            guideItemVenuePhoneWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenuePhoneWrapper.setVisibility(View.VISIBLE);
            guideItemVenuePhone.setText(phone);
            guideItemVenuePhone.setEnabled(false);
        }
    }

    private void renderWebsite(GuideItem guideItem) {
        guideItemVenueWebsite.setCompoundDrawablesWithIntrinsicBounds(null, null, websiteIcon, null);
        guideItemVenueWebsiteMarquee.setSelected(true);
        final String website = guideItem.getWebsite();
        if (website == null || website.isEmpty()) {
            guideItemVenueWebsiteWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenueWebsiteWrapper.setVisibility(View.VISIBLE);
            guideItemVenueWebsiteMarquee.setVisibility(View.VISIBLE);
            guideItemVenueWebsiteMarquee.setText(getContext()
                    .getString(R.string.label_website_icon) + "   " + website);
            guideItemVenueWebsiteMarquee.setSelected(true);
            guideItemVenueWebsite.setVisibility(View.INVISIBLE);
            guideItemVenueWebsite.setHeight(0);
            guideItemVenueWebsite.setText(website);
            guideItemVenueWebsite.setEnabled(false);
        }
    }

    private void renderEmail(GuideItem guideItem) {
        guideItemVenueEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, emailIcon, null);
        final String email = guideItem.getEmail();
        if (email == null || email.isEmpty()) {
            guideItemVenueEmailWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenueEmailWrapper.setVisibility(View.VISIBLE);
            guideItemVenueEmail.setText(email);
            guideItemVenueEmail.setEnabled(false);
        }
    }

    private void renderAddress(GuideItem guideItem) {
        guideItemVenueAddress.setCompoundDrawablesWithIntrinsicBounds(null, null, addressIcon, null);
        final String address = guideItem.getAddress();
        if (address == null || address.isEmpty()) {
            guideItemVenueAddressWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenueAddressWrapper.setVisibility(View.VISIBLE);
            guideItemVenueAddress.setText(address);
            guideItemVenueAddress.setEnabled(false);
        }
    }

    private void renderCost(GuideItem guideItem) {
        guideItemVenueCost.setText(guideItem.getCost());
        guideItemVenueCost.setSelected(true);
    }

}
