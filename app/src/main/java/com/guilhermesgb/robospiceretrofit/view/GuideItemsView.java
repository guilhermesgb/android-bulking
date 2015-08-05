package com.guilhermesgb.robospiceretrofit.view;

import android.content.Context;

import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

public interface GuideItemsView extends MvpLceView<List<GuideItem>> {

    Context getContext();

}
