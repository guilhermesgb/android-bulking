package com.guilhermesgb.robospiceretrofit.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.model.GuideItemCollection;
import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitSpiceService;
import com.guilhermesgb.robospiceretrofit.network.listeners.SubsequentRequestListener;
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
import java.util.UUID;

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
                Log.e(TAG, "Guide Items page retrieval interface not found!");
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
                new InitialGuideItemsRequestListener(guideItemsRequest, pullToRefresh));
    }

    private void continueSyncingGuideItems(GuideItemsRequest guideItemsRequest,
            final String syncUuid, final boolean pullToRefresh) {
        networkSpiceManager.execute(guideItemsRequest,
                guideItemsRequest.getCurrentResolvedRequestSignature(),
                DurationInMillis.ONE_MINUTE, new SubsequentRequestListener<JsonObject>(syncUuid) {

                    @Override
                    public void actUponThisRequestFailedDueToNoNetwork(Throwable exception) {
                        Log.e(TAG, "Retrieval of Guide Items from Backend failed: no network!");
                        if (isViewAttached() && getView() != null) {
                            getView().showError(exception, pullToRefresh);
                        }
                    }

                    @Override
                    public void actUponThisRequestCanceled() {
                        Log.e(TAG, "Retrieval of Guide Items from Backend canceled!");
                        if (isViewAttached() && getView() != null) {
                            getView().showContent();
                        }
                    }

                    @Override
                    public void actUponThisRequestFailed(Throwable exception) {
                        Log.e(TAG, "Retrieval of Guide Items from Backend failed!");
                        exception.printStackTrace();
                        if (isViewAttached() && getView() != null) {
                            getView().showError(exception, pullToRefresh);
                        }
                    }

                    @Override
                    public void actUponThisRequestSucceeded(JsonObject response) {
                        try {
                            GuideItem.parseAndSyncGuideItems(response);
                            restoreCachedGuideItems(pullToRefresh);
                        } catch (IOException ioException) {
                            Log.e(TAG, "Could not parse Guide Items retrieved from Backend!");
                            ioException.printStackTrace();
                            if (isViewAttached() && getView() != null) {
                                getView().showError(ioException, pullToRefresh);
                            }
                        }
                    }

                    @Override
                    public void actUponAllRequestsSucceeded() {
                        restoreCachedGuideItems(pullToRefresh);
                    }

                });
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
            Log.e(TAG, "Retrieval of Guide Items from Storage failed!");
            spiceException.printStackTrace();
            if (isViewAttached() && getView() != null) {
                getView().showError(spiceException, pullToRefresh);
            }
        }

        @Override
        public void onRequestSuccess(GuideItemCollection guideItems) {
            if (guideItems.size() > 0 && isViewAttached() && getView() != null) {
                getView().setData(guideItems);
                getView().showContent();
            }
        }

    }

    public final class InitialGuideItemsRequestListener implements RequestListener<JsonObject> {

        private GuideItemsRequest guideItemsRequest;
        private boolean pullToRefresh;

        public InitialGuideItemsRequestListener(GuideItemsRequest guideItemsRequest, boolean pullToRefresh) {
            super();
            this.guideItemsRequest = guideItemsRequest;
            this.pullToRefresh = pullToRefresh;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException instanceof NoNetworkException) {
                Log.e(TAG, "Retrieval of Guide Items from Backend failed: no network!");
                if (isViewAttached() && getView() != null) {
                    getView().showError(spiceException, pullToRefresh);
                }
            }
            else if (spiceException instanceof RequestCancelledException) {
                Log.e(TAG, "Retrieval of Guide Items from Backend canceled!");
                if (isViewAttached() && getView() != null) {
                    getView().showContent();
                }
            }
            else {
                Log.e(TAG, "Retrieval of Guide Items from Backend failed!");
                spiceException.printStackTrace();
                if (isViewAttached() && getView() != null) {
                    getView().showError(spiceException, pullToRefresh);
                }
            }
        }

        @Override
        public void onRequestSuccess(JsonObject response) {
            try {
                GuideItem.parseAndSyncGuideItems(response);
                final int remainingRequests = (response.get("pages").getAsInt() - 1);
                if (remainingRequests > 0) {
                    final String syncUuid = UUID.randomUUID().toString();
                    SubsequentRequestListener.reset(remainingRequests, syncUuid);
                    for (int i=0; i<remainingRequests; i++) {
                        guideItemsRequest = guideItemsRequest.getNext();
                        continueSyncingGuideItems(guideItemsRequest, syncUuid, pullToRefresh);
                    }
                }
            } catch (IOException ioException) {
                Log.e(TAG, "Could not parse Guide Items retrieved from Backend!");
                ioException.printStackTrace();
                if (isViewAttached() && getView() != null) {
                    getView().showError(ioException, pullToRefresh);
                }
            }
        }

    }

}
