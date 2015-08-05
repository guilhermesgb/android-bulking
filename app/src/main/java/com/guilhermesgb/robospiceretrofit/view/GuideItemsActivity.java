package com.guilhermesgb.robospiceretrofit.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.model.GuideItemCollection;
import com.guilhermesgb.robospiceretrofit.presenter.GuideItemsPresenter;
import com.guilhermesgb.robospiceretrofit.presenter.SimpleGuideItemsPresenter;
import com.guilhermesgb.robospiceretrofit.renderers.GuideItemRendererBuilder;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.pedrogomez.renderers.RendererAdapter;

import java.util.List;

import butterknife.Bind;

public class GuideItemsActivity
        extends MvpLceActivity<SwipeRefreshLayout, List<GuideItem>, GuideItemsView, GuideItemsPresenter>
        implements GuideItemsView, SwipeRefreshLayout.OnRefreshListener {

    private RendererAdapter<GuideItem> adapter;
    @Bind(R.id.contentView) SwipeRefreshLayout contentView;
    @Bind(R.id.list_view) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_items);
        contentView.setOnRefreshListener(this);
        adapter = new RendererAdapter<>(getLayoutInflater(),
                new GuideItemRendererBuilder(getApplicationContext()),
                new GuideItemCollection(GuideItem.retrieveStoredGuideItems()));
        listView.setAdapter(adapter);
        loadData(false);
    }

    @NonNull
    @Override
    public GuideItemsPresenter createPresenter() {
        return new SimpleGuideItemsPresenter();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void setData(List<GuideItem> guideItems) {
        adapter.clear();
        adapter.addAll(guideItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        Log.v("GuideItemsActivity", "TRIGGERING TRUE REFRESH!");
        loadData(true);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadGuideItems(pullToRefresh);
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable error, boolean pullToRefresh) {
        super.showError(error, pullToRefresh);
        contentView.setRefreshing(false);
    }

    @Override
    protected String getErrorMessage(Throwable throwable, boolean pullToRefresh) {
        if (pullToRefresh) {
            return this.getString(R.string.txt_error_guide_items);
        }
        else {
            return this.getString(R.string.txt_error_guide_items_retry);
        }
    }

}
