package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.pedrogomez.renderers.Renderer;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class GuideItemRenderer extends Renderer<GuideItem> {

    private final Context context;

    @Bind(R.id.guide_item_name) TextView guideItemName;
    @Bind(R.id.guide_item_cover) ImageView guideItemCover;
    @Bind(R.id.guide_item_category) TextView guideItemCategory;
    @Bind(R.id.guide_item_short_description) TextView guideItemShortDescription;

    public GuideItemRenderer(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void setUpView(View view) {}

    @Override
    protected void hookListeners(View view) {}

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        View inflatedView = inflater.inflate(getLayoutResource(), parent, false);
        ButterKnife.bind(this, inflatedView);
        return inflatedView;
    }

    protected abstract int getLayoutResource();

    @Override
    public void render() {
        GuideItem guideItem = getContent();
        renderName(guideItem);
        renderCover(guideItem);
        renderCategory(guideItem);
        renderShortDescription(guideItem);
        continueRendering(guideItem);
    }

    protected abstract void continueRendering(GuideItem guideItem);

    private void renderName(GuideItem guideItem) {
        this.guideItemName.setText(guideItem.getName());
    }

    private void renderCover(GuideItem guideItem) {
        final String imagePath = guideItem.getImageUrl();
        if (imagePath == null || imagePath.isEmpty()) {
            guideItemCover.setImageResource(R.drawable.cover_jfl_alarmes_backup);
            return;
        }
        Picasso.with(getContext())
                .load(imagePath)
                .error(R.drawable.cover_jfl_alarmes)
                .into(guideItemCover);
    }

    private void renderCategory(GuideItem guideItem) {
        this.guideItemCategory.setText(guideItem.getCategory());
    }

    private void renderShortDescription(GuideItem guideItem) {
        this.guideItemShortDescription.setText(guideItem.getShortDescription());
    }

}
