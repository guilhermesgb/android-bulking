package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;

import butterknife.Bind;

public abstract class VenueGuideItemRenderer extends GuideItemRenderer {

    @Bind(R.id.guide_item_venue_phone) EditText guideItemVenuePhone;
    @Bind(R.id.guide_item_venue_phone_wrapper) TextInputLayout guideItemVenuePhoneWrapper;
    @Bind(R.id.guide_item_venue_website) EditText guideItemVenueWebsite;
    @Bind(R.id.guide_item_venue_website_wrapper) TextInputLayout guideItemVenueWebsiteWrapper;
    @Bind(R.id.guide_item_venue_email) EditText guideItemVenueEmail;
    @Bind(R.id.guide_item_venue_email_wrapper) TextInputLayout guideItemVenueEmailWrapper;
    @Bind(R.id.guide_item_venue_address) EditText guideItemVenueAddress;
    @Bind(R.id.guide_item_venue_address_wrapper) TextInputLayout guideItemVenueAddressWrapper;

    public VenueGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
         protected void continueRendering(GuideItem guideItem) {
        renderPhone(guideItem);
        renderWebsite(guideItem);
        renderEmail(guideItem);
        renderAddress(guideItem);
    }

    private void renderPhone(GuideItem guideItem) {
        final String phone = guideItem.getPhone();
        if (phone == null || phone.isEmpty()) {
            guideItemVenuePhoneWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenuePhoneWrapper.setVisibility(View.VISIBLE);
            guideItemVenuePhone.setText(phone);
        }
    }

    private void renderWebsite(GuideItem guideItem) {
        final String website = guideItem.getWebsite();
        if (website == null || website.isEmpty()) {
            guideItemVenueWebsiteWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenueWebsiteWrapper.setVisibility(View.VISIBLE);
            guideItemVenueWebsite.setText(website);
        }
    }

    private void renderEmail(GuideItem guideItem) {
        final String email = guideItem.getEmail();
        if (email == null || email.isEmpty()) {
            guideItemVenueEmailWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenueEmailWrapper.setVisibility(View.VISIBLE);
            guideItemVenueEmail.setText(email);
        }
    }

    private void renderAddress(GuideItem guideItem) {
        final String address = guideItem.getAddress();
        if (address == null || address.isEmpty()) {
            guideItemVenueAddressWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenueAddressWrapper.setVisibility(View.VISIBLE);
            guideItemVenueAddress.setText(address);
        }
    }

}
