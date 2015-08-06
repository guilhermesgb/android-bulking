package com.guilhermesgb.robospiceretrofit.network;

import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.SpiceServiceListener;

import java.util.ArrayList;
import java.util.List;

public abstract class CachedRequestsMonitor implements SpiceServiceListener {

    protected final List<String> requestCacheKeys = new ArrayList<>();
    private int successes = 0;
    private final int successesTarget;

    public CachedRequestsMonitor(int successesTarget) {
        this.successesTarget = successesTarget;
    }

    public synchronized void addSuccess() {
        successes++;
    }

    public synchronized int getSuccesses() {
        return successes;
    }

    public void addRequestCacheKey(String requestCacheKey) {
        requestCacheKeys.add(requestCacheKey);
    }

    public boolean successTargetReached() {
        return getSuccesses() == successesTarget;
    }

    @Override
    public void onRequestSucceeded(CachedSpiceRequest<?> request,
                                   RequestProcessingContext requestProcessingContext) {
        String requestCacheKey = (String) request.getRequestCacheKey();
        if (requestCacheKeys.contains(requestCacheKey)) {
            synchronized (this) {
                addSuccess();
                if (successTargetReached()) {
                    actUponAllRequestsSucceeded();
                }
            }
        }
    }

    public abstract void actUponAllRequestsSucceeded();

    @Override
    public void onRequestFailed(CachedSpiceRequest<?> request,
                                RequestProcessingContext requestProcessingContext) {
        String requestCacheKey = (String) request.getRequestCacheKey();
        if (requestCacheKeys.contains(requestCacheKey)) {
            actUponSomeRequestFailed();
        }
    }

    public abstract void actUponSomeRequestFailed();

    @Override
    public void onRequestCancelled(CachedSpiceRequest<?> request,
                                   RequestProcessingContext requestProcessingContext) {
        String requestCacheKey = (String) request.getRequestCacheKey();
        if (requestCacheKeys.contains(requestCacheKey)) {
            actUponSomeRequestCanceled();
        }
    }

    public abstract void actUponSomeRequestCanceled();

    public void reset() {
        requestCacheKeys.clear();
        successes = 0;
    }

    @Override
    public void onRequestProgressUpdated(CachedSpiceRequest<?> request,
                                         RequestProcessingContext requestProcessingContext) {}

    @Override
    public void onRequestAdded(CachedSpiceRequest<?> request,
                               RequestProcessingContext requestProcessingContext) {}

    @Override
    public void onRequestAggregated(CachedSpiceRequest<?> request,
                                    RequestProcessingContext requestProcessingContext) {}

    @Override
    public void onRequestNotFound(CachedSpiceRequest<?> request,
                                  RequestProcessingContext requestProcessingContext) {}

    @Override
    public void onRequestProcessed(CachedSpiceRequest<?> cachedSpiceRequest,
                                   RequestProcessingContext requestProcessingContext) {}

    @Override
    public void onServiceStopped() {}

}
