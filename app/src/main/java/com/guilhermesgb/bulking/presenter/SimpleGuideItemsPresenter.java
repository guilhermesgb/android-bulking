package com.guilhermesgb.bulking.presenter;

import android.util.Log;

import com.google.gson.JsonObject;
import com.guilhermesgb.bulking.model.storage.requests.FetchStoredGuideItemsRequest;
import com.guilhermesgb.bulking.model.storage.requests.SyncStoredGuideItemsRequest;
import com.guilhermesgb.bulking.presenter.network.listeners.SubsequentRequestListener;
import com.guilhermesgb.bulking.presenter.network.requests.FetchRemoteGuideItemsRequest;
import com.guilhermesgb.bulking.view.renderers.GuideItemCollection;
import com.guilhermesgb.bulking.view.GuideItemsView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.UUID;

public class SimpleGuideItemsPresenter extends MvpBasePresenter<GuideItemsView>
        implements GuideItemsPresenter {

    private static final String TAG = "GuideItemsPresenter";

    @Override
    public void loadGuideItems(boolean pullToRefresh) {
        if (isViewAttached() && getView() != null) {
            getView().showLoading(pullToRefresh);
        }
        if (!pullToRefresh) {
            restoreCachedGuideItems(false);
            return;
        }
        FetchRemoteGuideItemsRequest guideItemsRequest = FetchRemoteGuideItemsRequest.buildRequest();
        if (guideItemsRequest == null) {
            Log.e(TAG, "Guide Items page retrieval interface not found!");
            showErrorIfNoContentAvailable(null, true);
            return;
        }
        syncGuideItems(guideItemsRequest, true);
    }

    private void restoreCachedGuideItems(boolean pullToRefresh) {
        if (isViewAttached() && getView() != null) {
            Log.v(TAG, "FETCH STORED RESULTS");
            getView().getStorageSpiceManager().execute(new FetchStoredGuideItemsRequest(),
                    new StoredGuideItemsRequestListener(null, pullToRefresh));
        }
        else {
            Log.v(TAG, "VIEW IS NULL, ABORTING FETCH STORED RESULTS");
        }
    }

    private void syncGuideItems(FetchRemoteGuideItemsRequest guideItemsRequest, boolean pullToRefresh) {
        if (isViewAttached() && getView() != null) {
            Log.v(TAG, "EXECUTE " + guideItemsRequest.getCurrentResolvedRequestSignature());
            getView().getNetworkSpiceManager().execute(guideItemsRequest,
                    guideItemsRequest.getCurrentResolvedRequestSignature(),
                    (pullToRefresh ? DurationInMillis.ALWAYS_EXPIRED : DurationInMillis.ONE_DAY),
                    new InitialGuideItemsRequestListener(guideItemsRequest, pullToRefresh));
        }
        else {
            Log.v(TAG, "VIEW IS NULL, ABORTING EXECUTE " + guideItemsRequest.getCurrentResolvedRequestSignature());
        }
    }

    private void continueSyncingGuideItems(final FetchRemoteGuideItemsRequest guideItemsRequest,
            final String syncUuid, final boolean pullToRefresh) {
        if (isViewAttached() && getView() != null) {
            Log.v(TAG, "EXECUTE " + guideItemsRequest.getCurrentResolvedRequestSignature());
            getView().getNetworkSpiceManager().execute(guideItemsRequest,
                guideItemsRequest.getCurrentResolvedRequestSignature(),
                (pullToRefresh ? DurationInMillis.ALWAYS_EXPIRED : DurationInMillis.ONE_DAY),
                new SubsequentRequestListener<JsonObject>(syncUuid) {

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
                    Log.v(TAG, "RESPONSE SUCCESSFUL FOR " + guideItemsRequest.getCurrentResolvedRequestSignature());
                    if (isViewAttached() && getView() != null) {
                        getView().getStorageSpiceManager().execute(
                                new SyncStoredGuideItemsRequest(response), new RequestListener<Void>() {

                            @Override
                            public void onRequestFailure(SpiceException spiceException) {
                                Log.e(TAG, "Could not parse Guide Items retrieved from Backend!");
                                spiceException.printStackTrace();
                                showErrorIfNoContentAvailable(spiceException, pullToRefresh);
                            }

                            @Override
                            public void onRequestSuccess(Void voidValue) {
                                Log.v(TAG, "PARSED RESPONSE FOR " + guideItemsRequest.getCurrentResolvedRequestSignature());
                                totallySucceeded();
                            }

                        });
                    }
                    else {
                        Log.v(TAG, "VIEW IS NULL, ABORTING SUCCESS OF RESPONSE OF " + guideItemsRequest.getCurrentResolvedRequestSignature());
                    }
                }

                @Override
                public void actUponAllRequestsSucceeded() {
                    Log.v(TAG, "ALL SUBSEQUENT REQUESTS SUCCEEDED, LAST WAS " + guideItemsRequest.getCurrentResolvedRequestSignature());
                    restoreCachedGuideItems(pullToRefresh);
                }

            });
        }
        else {
            Log.v(TAG, "VIEW IS NULL, ABORTING EXECUTE " + guideItemsRequest.getCurrentResolvedRequestSignature());
        }
    }

    public void showAvailableContent() {
        if (isViewAttached() && getView() != null) {
            getView().showContent();
        }
    }

    public void showErrorIfNoContentAvailable(final Throwable exception, final boolean pullToRefresh) {
        if (isViewAttached() && getView() != null) {
            Log.v(TAG, "FETCH STORED RESULTS AND SHOW ERROR IF NONE PRESENT");
            getView().getStorageSpiceManager().execute(new FetchStoredGuideItemsRequest(),
                    new StoredGuideItemsRequestListener(exception, pullToRefresh));
        }
        else {
            Log.v(TAG, "VIEW IS NULL, ABORTING FETCH STORED RESULTS AND SHOW ERROR IF NONE PRESENT");
        }
    }

    public void showError(Throwable exception, boolean pullToRefresh) {
        if (isViewAttached() && getView() != null) {
            getView().showError(exception, pullToRefresh);
        }
    }

    public final class StoredGuideItemsRequestListener implements RequestListener<GuideItemCollection> {

        private final boolean pullToRefresh;
        private final Throwable exception;

        public StoredGuideItemsRequestListener(Throwable exception, boolean pullToRefresh) {
            super();
            this.pullToRefresh = pullToRefresh;
            this.exception = exception;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, "Retrieval of Guide Items from Storage failed!");
            spiceException.printStackTrace();
            showError(spiceException, pullToRefresh);
        }

        @Override
        public void onRequestSuccess(GuideItemCollection guideItems) {
            if (guideItems.size() > 0 && isViewAttached() && getView() != null) {
                getView().setData(guideItems);
                getView().showContent();
            }
            else {
                if (pullToRefresh) {
                    showError(exception, true);
                }
                else {
                    FetchRemoteGuideItemsRequest guideItemsRequest = FetchRemoteGuideItemsRequest.buildRequest();
                    if (guideItemsRequest == null) {
                        Log.e(TAG, "Guide Items page retrieval interface not found!");
                        showError(exception, false);
                        return;
                    }
                    syncGuideItems(guideItemsRequest, false);
                }
            }
        }

    }

    public final class InitialGuideItemsRequestListener implements RequestListener<JsonObject> {

        private FetchRemoteGuideItemsRequest guideItemsRequest;
        private boolean pullToRefresh;

        public InitialGuideItemsRequestListener(FetchRemoteGuideItemsRequest guideItemsRequest,
                                                boolean pullToRefresh) {
            super();
            this.guideItemsRequest = guideItemsRequest;
            this.pullToRefresh = pullToRefresh;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException instanceof NoNetworkException) {
                Log.e(TAG, "Retrieval of Guide Items from Backend failed: no network!");
                showError(spiceException, pullToRefresh);
            }
            else if (spiceException instanceof RequestCancelledException) {
                Log.e(TAG, "Retrieval of Guide Items from Backend canceled!");
                showAvailableContent();
            } else {
                Log.e(TAG, "Retrieval of Guide Items from Backend failed!");
                spiceException.printStackTrace();
                showError(spiceException, pullToRefresh);
            }
        }

        @Override
        public void onRequestSuccess(final JsonObject response) {
            if (isViewAttached() && getView() != null) {
                Log.v(TAG, "RESPONSE SUCCESSFUL FOR " + guideItemsRequest.getCurrentResolvedRequestSignature());
                getView().getStorageSpiceManager().execute(new SyncStoredGuideItemsRequest(response),
                    new RequestListener<Void>() {

                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        Log.e(TAG, "Could not parse Guide Items retrieved from Backend!");
                        spiceException.printStackTrace();
                        showError(spiceException, pullToRefresh);
                    }

                    @Override
                    public void onRequestSuccess(Void voidValue) {
                        Log.v(TAG, "PARSED RESPONSE FOR " + guideItemsRequest.getCurrentResolvedRequestSignature());
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
            else {
                Log.v(TAG, "VIEW IS NULL, ABORTING PROCESSING OF RESPONSE OF " + guideItemsRequest.getCurrentResolvedRequestSignature());
            }
        }

    }

}
