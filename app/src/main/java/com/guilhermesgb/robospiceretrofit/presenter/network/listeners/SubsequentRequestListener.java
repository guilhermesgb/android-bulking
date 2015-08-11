package com.guilhermesgb.robospiceretrofit.presenter.network.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class SubsequentRequestListener<T> implements RequestListener<T> {

    private static final Lock lock = new ReentrantLock();
    private static final Map<String, Integer> successes = new HashMap<>();
    private static final Map<String, Integer> successTargets = new HashMap<>();

    private final String syncUuid;

    public SubsequentRequestListener(String syncUuid) {
        super();
        this.syncUuid = syncUuid;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        if (spiceException instanceof NoNetworkException) {
            actUponThisRequestFailedDueToNoNetwork(spiceException);
        }
        else if (spiceException instanceof RequestCancelledException) {
            actUponThisRequestCanceled();
        }
        else {
            actUponThisRequestFailed(spiceException);
        }
    }

    public abstract void actUponThisRequestFailedDueToNoNetwork(Throwable exception);

    public abstract void actUponThisRequestCanceled();

    public abstract void actUponThisRequestFailed(Throwable exception);

    @Override
    public void onRequestSuccess(T response) {
        actUponThisRequestSucceeded(response);
    }

    public void actUponThisRequestSucceeded(T response) {
        totallySucceeded();
    }

    public void totallySucceeded() {
        synchronized (lock) {
            addSuccess(syncUuid);
            if (successTargetReached(syncUuid)) {
                actUponAllRequestsSucceeded();
            }
        }
    }

    public abstract void actUponAllRequestsSucceeded();

    public static boolean successTargetReached(String syncUuid) {
        return getSuccesses(syncUuid) == getSuccessTarget(syncUuid);
    }

    public static synchronized void addSuccess(String syncUuid) {
        successes.put(syncUuid, getSuccesses(syncUuid) + 1);
    }

    public static synchronized int getSuccesses(String syncUuid) {
        return successes.get(syncUuid);
    }

    public static synchronized int getSuccessTarget(String syncUuid) {
        return successTargets.get(syncUuid);
    }

    public static void reset(int target, String syncUuid) {
        successes.put(syncUuid, 0);
        successTargets.put(syncUuid, target);
    }

}
