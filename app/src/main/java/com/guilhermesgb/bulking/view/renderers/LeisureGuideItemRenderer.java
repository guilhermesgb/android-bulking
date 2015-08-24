package com.guilhermesgb.bulking.view.renderers;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.guilhermesgb.bulking.R;
import com.guilhermesgb.bulking.model.GuideItem;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import butterknife.Bind;

public class LeisureGuideItemRenderer extends GuideItemRenderer {

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

    public LeisureGuideItemRenderer(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.guide_item_leisure;
    }

    @Override
    public void continueRendering(GuideItem guideItem) {
        renderPhone(guideItem);
        renderWebsite(guideItem);
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


    private void renderVenueContactProperty(final EditText contactProperty, final TextView contactPropertyMarquee,
                              final TextInputLayout contactPropertyWrapper, final IconDrawable contactPropertyIcon,
                                          final int contactPropertyIconRes, String contactPropertyValue) {
        if (contactPropertyValue == null || contactPropertyValue.isEmpty()) {
            contactPropertyValue = "No value set";
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
        final View.OnFocusChangeListener existingListener = contactProperty.getOnFocusChangeListener();
        if (!(existingListener instanceof DisableEditModeOnFocusChangeListener)) {
            contactProperty.setOnFocusChangeListener(new DisableEditModeOnFocusChangeListener(contactProperty,
                    contactPropertyMarquee, contactPropertyIconRes, existingListener));
        }
        disableVenueContactPropertyEditMode(contactProperty,
                contactPropertyMarquee, contactPropertyIconRes);
    }

    class DisableEditModeOnFocusChangeListener implements View.OnFocusChangeListener {

        private final EditText contactProperty;
        private final TextView contactPropertyMarquee;
        private final int contactPropertyIconRes;
        private final View.OnFocusChangeListener previousListener;

        public DisableEditModeOnFocusChangeListener(final EditText contactProperty,
             final TextView contactPropertyMarquee, final int contactPropertyIconRes,
                                                    final View.OnFocusChangeListener previousListener) {
            this.previousListener = previousListener;
            this.contactProperty = contactProperty;
            this.contactPropertyMarquee = contactPropertyMarquee;
            this.contactPropertyIconRes = contactPropertyIconRes;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            previousListener.onFocusChange(view, hasFocus);
            if (!hasFocus) {
                disableVenueContactPropertyEditMode(contactProperty,
                        contactPropertyMarquee, contactPropertyIconRes);
            }
        }

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
        contactPropertyMarquee.setText(contactProperty.getText() + "   " + getContext()
                .getString(contactPropertyIconRes));
        return true;
    }

}
