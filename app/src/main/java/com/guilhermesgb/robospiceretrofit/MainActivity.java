package com.guilhermesgb.robospiceretrofit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitSpiceService;

import com.guilhermesgb.robospiceretrofit.network.network.requests.GuideItemsRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import retrofit.client.Response;

public class MainActivity extends Activity {

    private SpiceManager spiceManager = new SpiceManager(WordPressCMSRetrofitSpiceService.class);

    private GuideItemsRequest guideItemsRequest = new GuideItemsRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
        guideItemsRequest.setPage(1);
        spiceManager.execute(guideItemsRequest, guideItemsRequest.getCurrentResolvedRequestSignature(),
                DurationInMillis.ONE_MINUTE, new GuideItemsRequestListener());
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    public final class GuideItemsRequestListener implements RequestListener<Response> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(MainActivity.this,
                    "Retrieval of Guide Items page failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(Response response) {
            Toast.makeText(MainActivity.this,
                    "Retrieval of Guide Items page succeeded", Toast.LENGTH_SHORT).show();
        }

    }

}
