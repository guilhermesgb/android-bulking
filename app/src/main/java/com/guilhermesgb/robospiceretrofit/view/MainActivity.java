package com.guilhermesgb.robospiceretrofit.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.guilhermesgb.robospiceretrofit.R;
import com.hannesdorfmann.mosby.MosbyActivity;

import butterknife.Bind;

public class MainActivity extends MosbyActivity {

    @Bind(R.id.coordinatorLayout) CoordinatorLayout coordinatorLayout;
    @Bind(R.id.collapsingToolbarLayout) CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.appToolbar) Toolbar toolbar;
    @Bind(R.id.viewPager) ViewPager viewPager;
    @Bind(R.id.floatingActionButton) FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar.setNavigationIcon(R.drawable.notification_icon);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.app_name));
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return new GuideItemsFragment();
            }

            @Override
            public int getCount() {
                return 1;
            }

        };
        viewPager.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(coordinatorLayout, "FAB Clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

}
