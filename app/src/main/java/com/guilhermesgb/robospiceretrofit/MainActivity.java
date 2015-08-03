package com.guilhermesgb.robospiceretrofit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.guilhermesgb.robospiceretrofit.model.GuideItem;
import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitSpiceService;

import com.guilhermesgb.robospiceretrofit.network.network.requests.GuideItemsRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.io.IOException;
import java.util.List;

import retrofit.client.Response;

public class MainActivity extends Activity {

    private SpiceManager spiceManager = new SpiceManager(WordPressCMSRetrofitSpiceService.class);

    private GuideItemsRequest guideItemsRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            guideItemsRequest = new GuideItemsRequest(1);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            Toast.makeText(MainActivity.this,
                    "Guide Items page retrieval interface not found", Toast.LENGTH_SHORT).show();
            finish();
        }
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
            spiceException.printStackTrace();
            Toast.makeText(MainActivity.this,
                    "Retrieval of Guide Items page failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestSuccess(Response response) {
            try {
                List<GuideItem> guideItems = GuideItem.parseGuideItems(response);
                Toast.makeText(MainActivity.this,
                        "Retrieval of Guide Items page succeeded", Toast.LENGTH_SHORT).show();
                for (GuideItem guideItem : guideItems) {
                    System.out.println(guideItem.getName() + " (" + guideItem.getId() + ")");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "Retrieval of Guide Items page succeeded but failed to parse",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

}
