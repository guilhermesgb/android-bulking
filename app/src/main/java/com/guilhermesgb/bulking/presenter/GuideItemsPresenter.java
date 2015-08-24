package com.guilhermesgb.bulking.presenter;

import com.guilhermesgb.bulking.view.GuideItemsView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

public interface GuideItemsPresenter extends MvpPresenter<GuideItemsView> {

    void loadGuideItems(final boolean pullToRefresh);

}
