package com.guilhermesgb.robospiceretrofit.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.guilhermesgb.robospiceretrofit.R;
import com.hannesdorfmann.mosby.MosbyActivity;

import butterknife.Bind;

public class MainActivity extends MosbyActivity {

    @Bind(R.id.appToolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_frame);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    new GuideItemsFragment()).commit();
        }
    }

}
