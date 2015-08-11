package com.guilhermesgb.robospiceretrofit.view;

import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.octo.android.robospice.SpiceManager;

import java.util.List;

public interface GuideItemsView extends MvpLceView<List<GuideItem>> {

    SpiceManager getNetworkSpiceManager();

    SpiceManager getStorageSpiceManager();

}
