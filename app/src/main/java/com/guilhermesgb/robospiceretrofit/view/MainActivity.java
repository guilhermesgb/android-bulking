package com.guilhermesgb.robospiceretrofit.view;

import android.os.Bundle;

import com.guilhermesgb.robospiceretrofit.R;
import com.hannesdorfmann.mosby.MosbyActivity;

public class MainActivity extends MosbyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_frame);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    new GuideItemsFragment()).commit();
        }
    }

}
