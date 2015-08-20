package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.view.View;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.view.widgets.IconEditText;
import com.guilhermesgb.robospiceretrofit.view.widgets.IconTextInputLayout;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import butterknife.Bind;

public abstract class VenueGuideItemRenderer extends GuideItemRenderer {

    @Bind(R.id.guide_item_venue_phone) IconEditText guideItemVenuePhone;
    @Bind(R.id.guide_item_venue_phone_wrapper) IconTextInputLayout guideItemVenuePhoneWrapper;
    final IconDrawable phoneIcon = new IconDrawable(getContext(),
            MaterialIcons.md_local_phone).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    final String ICON_PHONE = "{md_local_phone}";
    @Bind(R.id.guide_item_venue_website) IconEditText guideItemVenueWebsite;
    @Bind(R.id.guide_item_venue_website_wrapper)
    IconTextInputLayout guideItemVenueWebsiteWrapper;
    final IconDrawable websiteIcon = new IconDrawable(getContext(),
            MaterialIcons.md_language).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    final String ICON_WEBSITE = "{md_language}";
    @Bind(R.id.guide_item_venue_email) IconEditText guideItemVenueEmail;
    @Bind(R.id.guide_item_venue_email_wrapper) IconTextInputLayout guideItemVenueEmailWrapper;
    final IconDrawable emailIcon = new IconDrawable(getContext(),
            MaterialIcons.md_local_post_office).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    final String ICON_EMAIL = "{md_local_post_office}";
    @Bind(R.id.guide_item_venue_address) IconEditText guideItemVenueAddress;
    @Bind(R.id.guide_item_venue_address_wrapper) IconTextInputLayout guideItemVenueAddressWrapper;
    final IconDrawable addressIcon = new IconDrawable(getContext(),
            MaterialIcons.md_location_on).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    final String ICON_ADDRESS = "{md_location_on}";

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
        guideItemVenuePhoneWrapper.setHint(getContext().getString(R.string.label_email), ICON_PHONE);
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
        guideItemVenueWebsiteWrapper.setHint(getContext().getString(R.string.label_email), ICON_WEBSITE);
        guideItemVenueWebsite.setCompoundDrawablesWithIntrinsicBounds(null, null, websiteIcon, null);
        final String website = guideItem.getWebsite();
        if (website == null || website.isEmpty()) {
            guideItemVenueWebsiteWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenueWebsiteWrapper.setVisibility(View.VISIBLE);
            guideItemVenueWebsite.setText(website);
            guideItemVenueWebsite.setEnabled(false);
        }
    }

    private void renderEmail(GuideItem guideItem) {
        guideItemVenueEmailWrapper.setHint(getContext().getString(R.string.label_email), ICON_EMAIL);
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
        guideItemVenueAddressWrapper.setHint(getContext().getString(R.string.label_address), ICON_ADDRESS);
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

}
