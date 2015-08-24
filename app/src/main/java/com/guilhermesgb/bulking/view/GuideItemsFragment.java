package com.guilhermesgb.bulking.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.guilhermesgb.bulking.R;
import com.guilhermesgb.bulking.model.GuideItem;
import com.guilhermesgb.bulking.model.storage.OfflineSpiceService;
import com.guilhermesgb.bulking.presenter.GuideItemsPresenter;
import com.guilhermesgb.bulking.presenter.SimpleGuideItemsPresenter;
import com.guilhermesgb.bulking.presenter.network.WordPressCMSRetrofitSpiceService;
import com.guilhermesgb.bulking.view.renderers.GuideItemCollection;
import com.guilhermesgb.bulking.view.renderers.GuideItemsRendererBuilder;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.ParcelableLceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.CastedArrayListLceViewState;
import com.octo.android.robospice.SpiceManager;
import com.pedrogomez.renderers.RVRendererAdapter;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

public class GuideItemsFragment extends
        MvpLceViewStateFragment<SwipeRefreshLayout, List<GuideItem>, GuideItemsView, GuideItemsPresenter>
        implements GuideItemsView, SwipeRefreshLayout.OnRefreshListener {

    private RVRendererAdapter<GuideItem> adapter;
    private SpiceManager networkSpiceManager = new SpiceManager(WordPressCMSRetrofitSpiceService.class);
    private SpiceManager storageSpiceManager = new SpiceManager(OfflineSpiceService.class);
    private List<GuideItem> lastSeenData;

    @Bind(R.id.loadingView) ProgressBar loadingView;
    @Bind(R.id.contentView) SwipeRefreshLayout contentView;
    @Bind(R.id.recyclerView) RecyclerView recyclerView;
    @Bind(R.id.countView) TextView dataCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!networkSpiceManager.isStarted()) {
            networkSpiceManager.start(getActivity().getApplicationContext());
        }
        if (!storageSpiceManager.isStarted()) {
            storageSpiceManager.start(getActivity().getApplicationContext());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        loadingView.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.jfl_yellow), PorterDuff.Mode.SRC_IN);
        contentView.setOnRefreshListener(this);
        contentView.setColorSchemeResources(R.color.jfl_blue, R.color.jfl_yellow);
        adapter = new RVRendererAdapter<>(getActivity().getLayoutInflater(),
                new GuideItemsRendererBuilder(getActivity().getApplicationContext()),
                new GuideItemCollection(new LinkedList<GuideItem>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        dataCount.setText("0");
    }

    @Override
    public void onDestroy() {
        if (networkSpiceManager.isStarted()) {
            networkSpiceManager.shouldStop();
        }
        if (storageSpiceManager.isStarted()) {
            storageSpiceManager.shouldStop();
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_guide_items;
    }

    @NonNull
    @Override
    public GuideItemsPresenter createPresenter() {
        return new SimpleGuideItemsPresenter();
    }

    @Override
    public SpiceManager getNetworkSpiceManager() {
        return networkSpiceManager;
    }

    @Override
    public SpiceManager getStorageSpiceManager() {
        return storageSpiceManager;
    }

    @NonNull
    @Override
    public ParcelableLceViewState<List<GuideItem>, GuideItemsView> createViewState() {
        return new CastedArrayListLceViewState<>();
    }

    @Override
    public List<GuideItem> getData() {
        return lastSeenData;
    }

    @Override
    public void setData(List<GuideItem> guideItems) {
        adapter.clear();
        if (guideItems != null) {
            adapter.addAll(guideItems);
            dataCount.setText(Integer.toString(guideItems.size()));
        }
        adapter.notifyDataSetChanged();
        lastSeenData = guideItems;
    }

    @Override
    public void onRefresh() {
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
    protected void showLightError(String msg) {
        if(this.getActivity() != null) {
            Snackbar.make(getCoordinatorLayout(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return ((MainActivity) getActivity()).getCoordinatorLayout();
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
