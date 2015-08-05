package com.guilhermesgb.robospiceretrofit.presenter;

import com.guilhermesgb.robospiceretrofit.view.GuideItemsView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

public interface GuideItemsPresenter extends MvpPresenter<GuideItemsView> {

    void loadGuideItems(final boolean pullToRefresh);

}
