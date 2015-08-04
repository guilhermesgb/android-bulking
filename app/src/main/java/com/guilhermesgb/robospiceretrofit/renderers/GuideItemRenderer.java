package com.guilhermesgb.robospiceretrofit.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.pedrogomez.renderers.Renderer;

public abstract class GuideItemRenderer extends Renderer<GuideItem> {

    private final Context context;

    private TextView guideItemName;
    private TextView guideItemCategory;

    public GuideItemRenderer(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    protected TextView getGuideItemName() {
        return guideItemName;
    }

    protected TextView getGuideItemCategory() {
        return guideItemCategory;
    }

    @Override
    protected void setUpView(View view) {
        guideItemName = (TextView) view.findViewById(R.id.guide_item_name);
        guideItemCategory = (TextView) view.findViewById(R.id.guide_item_category);
        setUpParticularitiesInView(view);
    }

    protected abstract void setUpParticularitiesInView(View view);

    @Override
    protected void hookListeners(View view) {

    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(getLayoutResource(), parent, false);
    }

    protected abstract int getLayoutResource();

    @Override
    public void render() {
        GuideItem guideItem = getContent();
        renderName(guideItem);
        renderCategory(guideItem);
    }

    private void renderName(GuideItem guideItem) {
        this.guideItemName.setText(guideItem.getName());
    }

    private void renderCategory(GuideItem guideItem) {
        this.guideItemCategory.setText(guideItem.getCategory());
    }

}
