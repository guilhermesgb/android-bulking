package com.guilhermesgb.bulking.view;

import com.guilhermesgb.bulking.model.GuideItem;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.octo.android.robospice.SpiceManager;

import java.util.List;

public interface GuideItemsView extends MvpLceView<List<GuideItem>> {

    SpiceManager getNetworkSpiceManager();

    SpiceManager getStorageSpiceManager();

}
