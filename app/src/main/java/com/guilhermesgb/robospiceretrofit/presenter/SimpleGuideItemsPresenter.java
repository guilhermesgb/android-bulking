package com.guilhermesgb.robospiceretrofit.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.model.GuideItemCollection;
import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitSpiceService;
import com.guilhermesgb.robospiceretrofit.network.requests.GuideItemsRequest;
import com.guilhermesgb.robospiceretrofit.storage.OfflineSpiceService;
import com.guilhermesgb.robospiceretrofit.storage.requests.StoredGuideItemsRequest;
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

    private SpiceManager networkSpiceManager = new SpiceManager(WordPressCMSRetrofitSpiceService.class);
    private SpiceManager storageSpiceManager = new SpiceManager(OfflineSpiceService.class);

    @Override
    public void attachView(GuideItemsView view) {
        super.attachView(view);
        networkSpiceManager.start(view.getContext());
        storageSpiceManager.start(view.getContext());
    }

    @Override
    public void loadGuideItems(boolean pullToRefresh) {
        GuideItemsRequest guideItemsRequest = GuideItemsRequest.buildRequest();
        if (!pullToRefresh) {
            restoreCachedGuideItems(false);
        }
        else {
            if (guideItemsRequest == null) {
                Log.d(TAG, "Guide Items page retrieval interface not found!");
                if (isViewAttached() && getView() != null) {
                    getView().showError(null, true);
                }
                return;
            }
        }
        if (isViewAttached() && getView() != null) {
            getView().showLoading(pullToRefresh);
        }
        if (guideItemsRequest != null) {
            syncGuideItems(guideItemsRequest, pullToRefresh);
        }
    }

    private void restoreCachedGuideItems(boolean pullToRefresh) {
        storageSpiceManager.execute(new StoredGuideItemsRequest(),
                new StoredGuideItemsRequestListener(pullToRefresh));
    }

    private void syncGuideItems(GuideItemsRequest guideItemsRequest, boolean pullToRefresh) {
        networkSpiceManager.execute(guideItemsRequest,
                guideItemsRequest.getCurrentResolvedRequestSignature(), DurationInMillis.ONE_MINUTE,
                new GuideItemsRequestListener(guideItemsRequest, pullToRefresh));
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        networkSpiceManager.shouldStop();
        storageSpiceManager.shouldStop();
    }

    public final class StoredGuideItemsRequestListener implements RequestListener<GuideItemCollection> {

        private boolean pullToRefresh;

        public StoredGuideItemsRequestListener(boolean pullToRefresh) {
            super();
            this.pullToRefresh = pullToRefresh;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.d(TAG, "Retrieval of Guide Items from Storage failed!");
            spiceException.printStackTrace();
            if (isViewAttached() && getView() != null) {
                getView().showError(spiceException, pullToRefresh);
            }
        }

        @Override
        public void onRequestSuccess(GuideItemCollection guideItems) {
            if (isViewAttached() && getView() != null) {
                getView().setData(guideItems);
                getView().showContent();
            }
        }

    }

    public final class GuideItemsRequestListener implements RequestListener<JsonObject> {

        private GuideItemsRequest guideItemsRequest;
        private boolean pullToRefresh;

        public GuideItemsRequestListener(GuideItemsRequest guideItemsRequest, boolean pullToRefresh) {
            super();
            this.guideItemsRequest = guideItemsRequest;
            this.pullToRefresh = pullToRefresh;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException instanceof NoNetworkException) {
                Log.d(TAG, "Retrieval of Guide Items from Backend failed: no network!");
                if (isViewAttached() && getView() != null) {
                    getView().showError(spiceException, pullToRefresh);
                }
            }
            else if (spiceException instanceof RequestCancelledException) {
                restoreCachedGuideItems(pullToRefresh);
            }
            else {
                Log.d(TAG, "Retrieval of Guide Items from Backend failed!");
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
                    syncGuideItems(guideItemsRequest.getNext(), pullToRefresh);
                }
            } catch (IOException ioException) {
                Log.d(TAG, "Could not parse Guide Items retrieved from Backend!");
                ioException.printStackTrace();
                if (isViewAttached() && getView() != null) {
                    getView().showError(ioException, pullToRefresh);
                }
            }
        }

    }

}
