package com.guilhermesgb.robospiceretrofit.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitSpiceService;
import com.guilhermesgb.robospiceretrofit.network.requests.GuideItemsRequest;
import com.guilhermesgb.robospiceretrofit.view.GuideItemsView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.IOException;
import java.util.List;

public class SimpleGuideItemsPresenter extends MvpBasePresenter<GuideItemsView>
        implements GuideItemsPresenter {

    private static final String TAG = "GuideItemsPresenter";

    private SpiceManager spiceManager = new SpiceManager(WordPressCMSRetrofitSpiceService.class);
    private GuideItemsRequest guideItemsRequest = GuideItemsRequest.buildRequest();

    @Override
    public void attachView(GuideItemsView view) {
        super.attachView(view);
        spiceManager.start(view.getContext());
    }

    @Override
    public void loadGuideItems(boolean pullToRefresh) {
        if (!pullToRefresh) {
            showCachedContent();
        }
        else {
            if (guideItemsRequest == null) {
                Log.d(TAG, "Guide Items page retrieval interface not found!");
                if (isViewAttached() && getView() != null) {
                    getView().showError(null, true);
                }
                return;
            }
            if (isViewAttached() && getView() != null) {
                getView().showLoading(true);
            }
            spiceManager.execute(guideItemsRequest,
                    guideItemsRequest.getCurrentResolvedRequestSignature(),
                    DurationInMillis.ONE_MINUTE, new GuideItemsRequestListener(true));
        }
    }

    private void showCachedContent() {
        if (isViewAttached() && getView() != null) {
            getView().setData(GuideItem.retrieveStoredGuideItems());
            getView().showContent();
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        spiceManager.shouldStop();
    }

    public final class GuideItemsRequestListener implements RequestListener<JsonObject> {

        private boolean pullToRefresh;

        public GuideItemsRequestListener(boolean pullToRefresh) {
            super();
            this.pullToRefresh = pullToRefresh;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException instanceof NoNetworkException) {
                Log.d(TAG, "Retrieval of Guide Items page failed: no network!");
                if (isViewAttached() && getView() != null) {
                    getView().showError(spiceException, pullToRefresh);
                }
            }
            else if (spiceException instanceof RequestCancelledException) {
                guideItemsRequest = GuideItemsRequest.buildRequest();
                showCachedContent();
            }
            else {
                Log.d(TAG, "Retrieval of Guide Items page failed!");
                spiceException.printStackTrace();
                if (isViewAttached() && getView() != null) {
                    getView().showError(spiceException, pullToRefresh);
                }
            }
        }

        @Override
        public void onRequestSuccess(JsonObject response) {
            try {
                List<GuideItem> guideItems = GuideItem.parseGuideItems(response);
                if (guideItems.size() != 0) {
                    spiceManager.execute(guideItemsRequest.getNext(),
                            guideItemsRequest.getCurrentResolvedRequestSignature(),
                            DurationInMillis.ONE_MINUTE, new GuideItemsRequestListener(pullToRefresh));
                }
            } catch (IOException ioException) {
                Log.d(TAG, "Retrieval of Guide Items page succeeded but failed to parse!");
                ioException.printStackTrace();
                if (isViewAttached() && getView() != null) {
                    getView().showError(ioException, pullToRefresh);
                }
            }
        }

    }

}
