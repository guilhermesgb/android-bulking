package com.guilhermesgb.robospiceretrofit.view;

import com.activeandroid.app.Application;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new MaterialModule());
    }
}
