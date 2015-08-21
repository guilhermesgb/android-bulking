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
    @Bind(R.id.guide_item_venue_email_marquee) TextView guideItemVenueEmailMarquee;
    @Bind(R.id.guide_item_venue_email_wrapper) TextInputLayout guideItemVenueEmailWrapper;
    final IconDrawable guideItemVenueEmailIcon = new IconDrawable(getContext(),
            MaterialIcons.md_local_post_office).colorRes(R.color.jfl_yellow)
            .sizeRes(R.dimen.venue_property_icon_size);
    @Bind(R.id.guide_item_venue_address) EditText guideItemVenueAddress;
    @Bind(R.id.guide_item_venue_address_marquee) TextView guideItemVenueAddressMarquee;
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
        renderVenueContactProperty(guideItemVenueEmail, guideItemVenueEmailMarquee,
                guideItemVenueEmailWrapper, guideItemVenueEmailIcon, R.string.label_email_icon, email);
    }

    private void renderAddress(GuideItem guideItem) {
        final String address = guideItem.getAddress();
        renderVenueContactProperty(guideItemVenueAddress, guideItemVenueAddressMarquee,
                guideItemVenueAddressWrapper, guideItemVenueAddressIcon, R.string.label_address_icon, address);
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
        contactPropertyMarquee.setVisibility(View.VISIBLE);
        contactPropertyMarquee.setSelected(true);
        contactPropertyMarquee.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                return enableVenueContactPropertyEditMode(contactProperty, contactPropertyMarquee);
            }

        });
        contactProperty.setText(contactPropertyValue);
        contactProperty.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                return disableVenueContactPropertyEditMode(contactProperty,
                        contactPropertyMarquee, contactPropertyIconRes);
            }

        });
/*        contactProperty.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    disableVenueContactPropertyEditMode(contactProperty,
                            contactPropertyMarquee, contactPropertyIconRes);
                }
            }

        });*/
        disableVenueContactPropertyEditMode(contactProperty,
                contactPropertyMarquee, contactPropertyIconRes);
    }

    private boolean enableVenueContactPropertyEditMode(final EditText contactProperty,
                                                       final TextView contactPropertyMarquee) {
        contactProperty.setVisibility(View.VISIBLE);
        contactProperty.setEnabled(true);
        contactPropertyMarquee.setVisibility(View.INVISIBLE);
        return true;
    }

    private boolean disableVenueContactPropertyEditMode(final EditText contactProperty,
                 final TextView contactPropertyMarquee, final int contactPropertyIconRes) {
        if (contactProperty.getText().toString().trim().isEmpty()) {
            return false;
        }
        contactProperty.setVisibility(View.INVISIBLE);
        contactProperty.setEnabled(false);
        contactPropertyMarquee.setVisibility(View.VISIBLE);
        contactPropertyMarquee.setSelected(true);
        contactPropertyMarquee.setText(contactProperty.getText() + "   " +  getContext()
                .getString(contactPropertyIconRes));
        return true;
    }

    private void renderCost(GuideItem guideItem) {
        guideItemVenueCost.setText(guideItem.getCost());
        guideItemVenueCost.setSelected(true);
    }

}
