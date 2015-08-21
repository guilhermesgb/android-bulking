package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
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
    @Bind(R.id.guide_item_venue_phone_marquee) TextView guideItemVenuePhoneMarquee;
    @Bind(R.id.guide_item_venue_phone_wrapper) TextInputLayout guideItemVenuePhoneWrapper;
    final IconDrawable guideItemVenuePhoneIcon = new IconDrawable(getContext(),
            MaterialIcons.md_local_phone).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    @Bind(R.id.guide_item_venue_website) EditText guideItemVenueWebsite;
    @Bind(R.id.guide_item_venue_website_marquee) TextView guideItemVenueWebsiteMarquee;
    @Bind(R.id.guide_item_venue_website_wrapper) TextInputLayout guideItemVenueWebsiteWrapper;
    final IconDrawable guideItemVenueWebsiteIcon = new IconDrawable(getContext(),
            MaterialIcons.md_language).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    @Bind(R.id.guide_item_venue_email) EditText guideItemVenueEmail;
    @Bind(R.id.guide_item_venue_email_wrapper) TextInputLayout guideItemVenueEmailWrapper;
    final IconDrawable guideItemVenueEmailIcon = new IconDrawable(getContext(),
            MaterialIcons.md_local_post_office).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    @Bind(R.id.guide_item_venue_address) EditText guideItemVenueAddress;
    @Bind(R.id.guide_item_venue_address_wrapper) TextInputLayout guideItemVenueAddressWrapper;
    final IconDrawable guideItemVenueAddressIcon = new IconDrawable(getContext(),
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
        final String phone = guideItem.getPhone();
        renderVenueContactProperty(guideItemVenuePhone, guideItemVenuePhoneMarquee,
                guideItemVenuePhoneWrapper, guideItemVenuePhoneIcon, R.string.label_phone_icon, phone);
    }

    private void renderWebsite(GuideItem guideItem) {
        final String website = guideItem.getWebsite();
        renderVenueContactProperty(guideItemVenueWebsite, guideItemVenueWebsiteMarquee,
                guideItemVenueWebsiteWrapper, guideItemVenueWebsiteIcon, R.string.label_website_icon, website);
    }

    private void renderEmail(GuideItem guideItem) {
        final String email = guideItem.getEmail();
        guideItemVenueEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, guideItemVenueEmailIcon, null);
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
        final String address = guideItem.getAddress();
        guideItemVenueAddress.setCompoundDrawablesWithIntrinsicBounds(null, null, guideItemVenueAddressIcon, null);
        if (address == null || address.isEmpty()) {
            guideItemVenueAddressWrapper.setVisibility(View.GONE);
        }
        else {
            guideItemVenueAddressWrapper.setVisibility(View.VISIBLE);
            guideItemVenueAddress.setText(address);
            guideItemVenueAddress.setEnabled(false);
        }
    }

    private void renderVenueContactProperty(EditText contactProperty, TextView contactPropertyMarquee,
            TextInputLayout contactPropertyWrapper, IconDrawable contactPropertyIcon,
            int contactPropertyIconRes, String contactPropertyValue) {
        contactPropertyMarquee.setSelected(true);
        contactProperty.setCompoundDrawablesWithIntrinsicBounds(null, null, contactPropertyIcon, null);
        if (contactPropertyValue == null || contactPropertyValue.isEmpty()) {
            contactPropertyWrapper.setVisibility(View.GONE);
        }
        else {
            contactPropertyWrapper.setVisibility(View.VISIBLE);
            contactPropertyMarquee.setVisibility(View.VISIBLE);
            contactPropertyMarquee.setText(getContext()
                    .getString(contactPropertyIconRes) + "   " + contactPropertyValue);
            contactPropertyMarquee.setSelected(true);
            contactProperty.setVisibility(View.INVISIBLE);
            contactProperty.setHeight(0);
            contactProperty.setText(contactPropertyValue);
            contactProperty.setEnabled(false);
        }
    }

    private void renderCost(GuideItem guideItem) {
        guideItemVenueCost.setText(guideItem.getCost());
        guideItemVenueCost.setSelected(true);
    }

}
