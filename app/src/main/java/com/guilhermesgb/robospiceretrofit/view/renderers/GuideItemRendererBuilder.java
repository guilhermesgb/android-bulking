package com.guilhermesgb.robospiceretrofit.view.renderers;

import android.content.Context;

import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GuideItemRendererBuilder extends RendererBuilder<GuideItem> {

    public GuideItemRendererBuilder(Context context) {
        Collection<Renderer<GuideItem>> prototypes = getPrototypes(context);
        setPrototypes(prototypes);
    }

    @Override
    protected Class getPrototypeClass(GuideItem guideItem) {
        return SimpleGuideItemRenderer.class;
    }

    private List<Renderer<GuideItem>> getPrototypes(Context context) {
        List<Renderer<GuideItem>> prototypes = new LinkedList<>();
        prototypes.add(new SimpleGuideItemRenderer(context));
        return prototypes;
    }

}
