package com.guilhermesgb.robospiceretrofit.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.model.GuideItemCollection;
import com.guilhermesgb.robospiceretrofit.model.storage.OfflineSpiceService;
import com.guilhermesgb.robospiceretrofit.model.storage.requests.StoredGuideItemsCountRequest;
import com.guilhermesgb.robospiceretrofit.model.storage.requests.StoredGuideItemsRequest;
import com.guilhermesgb.robospiceretrofit.model.storage.requests.SyncStoredGuideItemsRequest;
import com.guilhermesgb.robospiceretrofit.presenter.network.WordPressCMSRetrofitSpiceService;
import com.guilhermesgb.robospiceretrofit.presenter.network.listeners.SubsequentRequestListener;
import com.guilhermesgb.robospiceretrofit.presenter.network.requests.GuideItemsRequest;
import com.guilhermesgb.robospiceretrofit.view.GuideItemsView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

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
                showErrorIfNoContentAvailable(null, true);
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
                guideItemsRequest.getCurrentResolvedRequestSignature(), DurationInMillis.ONE_DAY,
                new InitialGuideItemsRequestListener(guideItemsRequest, pullToRefresh));
    }

    private void continueSyncingGuideItems(GuideItemsRequest guideItemsRequest,
            final String syncUuid, final boolean pullToRefresh) {
        networkSpiceManager.execute(guideItemsRequest,
                guideItemsRequest.getCurrentResolvedRequestSignature(),
                DurationInMillis.ALWAYS_EXPIRED, new SubsequentRequestListener<JsonObject>(syncUuid) {

                    @Override
                    public void actUponThisRequestFailedDueToNoNetwork(Throwable exception) {
                        Log.e(TAG, "Retrieval of Guide Items from Backend failed: no network!");
                        showErrorIfNoContentAvailable(exception, pullToRefresh);
                    }

                    @Override
                    public void actUponThisRequestCanceled() {
                        Log.e(TAG, "Retrieval of Guide Items from Backend canceled!");
                        showAvailableContent();
                    }

                    @Override
                    public void actUponThisRequestFailed(Throwable exception) {
                        Log.e(TAG, "Retrieval of Guide Items from Backend failed!");
                        exception.printStackTrace();
                        showErrorIfNoContentAvailable(exception, pullToRefresh);
                    }

                    @Override
                    public void actUponThisRequestSucceeded(JsonObject response) {
                        storageSpiceManager.execute(new SyncStoredGuideItemsRequest(response),
                                new RequestListener<Void>() {

                            @Override
                            public void onRequestFailure(SpiceException spiceException) {
                                Log.e(TAG, "Could not parse Guide Items retrieved from Backend!");
                                spiceException.printStackTrace();
                                showErrorIfNoContentAvailable(spiceException, pullToRefresh);
                            }

                            @Override
                            public void onRequestSuccess(Void voidValue) {
                                restoreCachedGuideItems(pullToRefresh);
                            }

                        });
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
            showErrorIfNoContentAvailable(spiceException, pullToRefresh);
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
                showErrorIfNoContentAvailable(spiceException, pullToRefresh);
            }
            else if (spiceException instanceof RequestCancelledException) {
                Log.e(TAG, "Retrieval of Guide Items from Backend canceled!");
                showAvailableContent();
            } else {
                Log.e(TAG, "Retrieval of Guide Items from Backend failed!");
                spiceException.printStackTrace();
                showErrorIfNoContentAvailable(spiceException, pullToRefresh);
            }
        }

        @Override
        public void onRequestSuccess(final JsonObject response) {
            storageSpiceManager.execute(new SyncStoredGuideItemsRequest(response),
                    new RequestListener<Void>() {

                @Override
                public void onRequestFailure(SpiceException spiceException) {
                    Log.e(TAG, "Could not parse Guide Items retrieved from Backend!");
                    spiceException.printStackTrace();
                    showErrorIfNoContentAvailable(spiceException, pullToRefresh);
                }

                @Override
                public void onRequestSuccess(Void voidValue) {
                    final int remainingRequests = (response.get("pages").getAsInt() - 1);
                    if (remainingRequests > 0) {
                        final String syncUuid = UUID.randomUUID().toString();
                        SubsequentRequestListener.reset(remainingRequests, syncUuid);
                        for (int i=0; i<remainingRequests; i++) {
                            guideItemsRequest = guideItemsRequest.getNext();
                            continueSyncingGuideItems(guideItemsRequest, syncUuid, pullToRefresh);
                        }
                    }
                }

            });
        }

    }

    public void showErrorIfNoContentAvailable(final Throwable exception, final boolean pullToRefresh) {
        storageSpiceManager.execute(new StoredGuideItemsCountRequest(), new RequestListener<Integer>() {

            @Override
            public void onRequestFailure(SpiceException spiceException) {
                if (isViewAttached() && getView() != null) {
                    getView().showError(spiceException, pullToRefresh);
                }
            }

            @Override
            public void onRequestSuccess(Integer guideItemsCount) {
                if (guideItemsCount == 0) {
                    if (isViewAttached() && getView() != null) {
                        getView().showError(exception, pullToRefresh);
                    }
                }
                else {
                    showAvailableContent();
                }
            }

        });
    }

    public void showAvailableContent() {
        if (isViewAttached() && getView() != null) {
            getView().showContent();
        }
    }

}
