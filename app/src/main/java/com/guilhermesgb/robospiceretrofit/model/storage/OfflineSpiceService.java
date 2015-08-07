package com.guilhermesgb.robospiceretrofit.model.storage;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Build;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.networkstate.NetworkStateChecker;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;

public class OfflineSpiceService extends SpiceService {

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        return new CacheManager();
    }

    @Override
    protected NetworkStateChecker getNetworkStateChecker() {
        return new NetworkStateChecker() {
            @Override
            public boolean isNetworkAvailable(Context context) {
                return true;
            }

            @Override
            public void checkPermissions(Context context) {}
        };
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Notification createDefaultNotification() {
        final Notification notification = super.createDefaultNotification();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN){
            notification.priority = Notification.PRIORITY_MIN;
        }
        return notification;
    }

}
