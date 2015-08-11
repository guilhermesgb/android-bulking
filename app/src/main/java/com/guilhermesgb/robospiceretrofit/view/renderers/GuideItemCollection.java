package com.guilhermesgb.robospiceretrofit.view.renderers;

import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.pedrogomez.renderers.ListAdapteeCollection;

import java.util.List;

public class GuideItemCollection extends ListAdapteeCollection<GuideItem> {

    public GuideItemCollection(List<GuideItem> guideItems) {
        super(guideItems);
    }

}
