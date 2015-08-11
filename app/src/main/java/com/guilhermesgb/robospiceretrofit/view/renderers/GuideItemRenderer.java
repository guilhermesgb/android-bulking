package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.pedrogomez.renderers.Renderer;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class GuideItemRenderer extends Renderer<GuideItem> {

    private final Context context;

    @Bind(R.id.guide_item_name) TextView guideItemName;
    @Bind(R.id.guide_item_category) TextView guideItemCategory;

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
        renderCategory(guideItem);
        continueRendering(guideItem);
    }

    protected abstract void continueRendering(GuideItem guideItem);

    private void renderName(GuideItem guideItem) {
        this.guideItemName.setText(guideItem.getName());
    }

    private void renderCategory(GuideItem guideItem) {
        this.guideItemCategory.setText(guideItem.getCategory());
    }

}
