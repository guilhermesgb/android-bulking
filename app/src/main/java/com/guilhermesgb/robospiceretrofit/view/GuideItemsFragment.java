package com.guilhermesgb.robospiceretrofit.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.guilhermesgb.robospiceretrofit.R;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.model.storage.OfflineSpiceService;
import com.guilhermesgb.robospiceretrofit.presenter.GuideItemsPresenter;
import com.guilhermesgb.robospiceretrofit.presenter.SimpleGuideItemsPresenter;
import com.guilhermesgb.robospiceretrofit.presenter.network.WordPressCMSRetrofitSpiceService;
import com.guilhermesgb.robospiceretrofit.view.renderers.GuideItemRendererBuilder;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.ParcelableLceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.CastedArrayListLceViewState;
import com.octo.android.robospice.SpiceManager;
import com.pedrogomez.renderers.RendererAdapter;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

public class GuideItemsFragment extends
        MvpLceViewStateFragment<SwipeRefreshLayout, List<GuideItem>, GuideItemsView, GuideItemsPresenter>
        implements GuideItemsView, SwipeRefreshLayout.OnRefreshListener {

    private RendererAdapter<GuideItem> adapter;
    private SpiceManager networkSpiceManager = new SpiceManager(WordPressCMSRetrofitSpiceService.class);
    private SpiceManager storageSpiceManager = new SpiceManager(OfflineSpiceService.class);
    private List<GuideItem> lastSeenData;

    @Bind(R.id.contentView) SwipeRefreshLayout contentView;
    @Bind(R.id.listView) ListView listView;
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
        contentView.setOnRefreshListener(this);
        adapter = new RendererAdapter<>(getActivity().getLayoutInflater(),
                new GuideItemRendererBuilder(getActivity().getApplicationContext()),
                new GuideItemCollection(new LinkedList<GuideItem>()));
        listView.setAdapter(adapter);
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
