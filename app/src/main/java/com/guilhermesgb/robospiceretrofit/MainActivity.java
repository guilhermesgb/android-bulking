package com.guilhermesgb.robospiceretrofit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.model.GuideItemCollection;
import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitSpiceService;
import com.guilhermesgb.robospiceretrofit.network.requests.GuideItemsRequest;
import com.guilhermesgb.robospiceretrofit.renderers.GuideItemRendererBuilder;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.pedrogomez.renderers.RendererAdapter;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    private SpiceManager spiceManager = new SpiceManager(WordPressCMSRetrofitSpiceService.class);
    private GuideItemsRequest guideItemsRequest;
    private RendererAdapter<GuideItem> adapter;
    private ProgressBar progressBar;
    private Button syncButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        guideItemsRequest = GuideItemsRequest.buildRequest();
        if (guideItemsRequest == null) {
            Toast.makeText(MainActivity.this,
                    "Guide Items page retrieval interface not found", Toast.LENGTH_SHORT).show();
            finish();
        }
        adapter = new RendererAdapter<>(getLayoutInflater(),
                new GuideItemRendererBuilder(getApplicationContext()),
                new GuideItemCollection(GuideItem.retrieveStoredGuideItems()));
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        syncButton = (Button) findViewById(R.id.sync_button);
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (guideItemsRequest == null) {
                    Toast.makeText(MainActivity.this,
                            "Guide Items page retrieval interface not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                spiceManager.execute(guideItemsRequest.getNext(), guideItemsRequest.getCurrentResolvedRequestSignature(),
                        DurationInMillis.ONE_MINUTE, new GuideItemsRequestListener());
            }
        });
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    public final class GuideItemsRequestListener implements RequestListener<JsonObject> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (spiceException instanceof NoNetworkException) {
                Toast.makeText(MainActivity.this,
                        "Retrieval of Guide Items page failed: no network!",
                        Toast.LENGTH_SHORT).show();
            }
            else if (spiceException instanceof RequestCancelledException) {
                adapter.clear();
                adapter.addAll(GuideItem.retrieveStoredGuideItems());
                adapter.notifyDataSetChanged();
                guideItemsRequest = GuideItemsRequest.buildRequest();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Retrieval of Guide Items page failed", Toast.LENGTH_SHORT).show();
                spiceException.printStackTrace();
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(JsonObject response) {
            try {
                List<GuideItem> guideItems = GuideItem.parseGuideItems(response);
                if (guideItems.size() != 0) {
                    spiceManager.execute(guideItemsRequest.getNext(),
                            guideItemsRequest.getCurrentResolvedRequestSignature(),
                            DurationInMillis.ONE_MINUTE, new GuideItemsRequestListener());
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "Retrieval of Guide Items page succeeded but failed to parse",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

}
