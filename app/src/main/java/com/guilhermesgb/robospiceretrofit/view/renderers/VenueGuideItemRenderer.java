package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
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
//    int contactPropertyOriginalHeight;

    public VenueGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected void continueRendering(GuideItem guideItem) {
//        determineOriginalContactPropertyHeight();
        renderPhone(guideItem);
        renderWebsite(guideItem);
        renderEmail(guideItem);
        renderAddress(guideItem);
        renderCost(guideItem);
    }

/*    private void determineOriginalContactPropertyHeight() {
        ViewTreeObserver observer = guideItemVenuePhone.getViewTreeObserver();
        ViewTreeObserver.OnGlobalLayoutListener listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contactPropertyOriginalHeight = guideItemVenuePhone.getHeight();
            }
        };
        observer.addOnGlobalLayoutListener(listener);
    }*/

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

    private void renderVenueContactProperty(final EditText contactProperty, final TextView contactPropertyMarquee,
            final TextInputLayout contactPropertyWrapper, final IconDrawable contactPropertyIcon,
            final int contactPropertyIconRes, final String contactPropertyValue) {
        if (contactPropertyValue == null || contactPropertyValue.isEmpty()) {
            contactPropertyWrapper.setVisibility(View.GONE);
            contactPropertyMarquee.setVisibility(View.GONE);
            return;
        }
        contactProperty.setCompoundDrawablesWithIntrinsicBounds(null, null, contactPropertyIcon, null);
        contactPropertyWrapper.setVisibility(View.VISIBLE);
        contactPropertyMarquee.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                contactProperty.setHeight(contactPropertyOriginalHeight);
//                Log.wtf("TAG_LOG", "SETTING HEIGHT OF " + contactPropertyValue + " TO: " + contactPropertyOriginalHeight);
                contactProperty.setVisibility(View.VISIBLE);
                contactProperty.setEnabled(true);
                contactPropertyMarquee.setVisibility(View.INVISIBLE);
                return true;
            }
        });
        contactProperty.setText(contactPropertyValue);
        contactProperty.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
//                contactProperty.setHeight(0);
                if (contactProperty.getText().toString().trim().isEmpty()) {
                    return false;
                }
                contactProperty.setVisibility(View.INVISIBLE);
                contactProperty.setEnabled(false);
                contactPropertyMarquee.setVisibility(View.VISIBLE);
                contactPropertyMarquee.setSelected(true);
                contactPropertyMarquee.setText(getContext()
                        .getString(contactPropertyIconRes) + "   " + contactProperty.getText());
                return true;
            }
        });
        contactProperty.performLongClick();
    }

    private void renderCost(GuideItem guideItem) {
        guideItemVenueCost.setText(guideItem.getCost());
        guideItemVenueCost.setSelected(true);
    }

}
