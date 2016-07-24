package test.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import test.adapters.BoardAdapter;
import test.models.Pin;
import test.utils.ImageCache;
import test.utils.JsonParser;
import test.utils.DataCache;
import test.utils.UniversalDownloader;

public class MainActivity extends AppCompatActivity
{
    private ProgressBar progress;

    private List<Pin> pins;

    private BoardAdapter boardAdapter;

    private static final String URL = "http://pastebin.com/raw/wgkJgazE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        fetchPins();
    }


    private void init()
    {
        DataCache.getInstance().init(50);
        ImageCache.getInstance().init(6);

        progress = (ProgressBar) findViewById(R.id.progress);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        pins = new ArrayList<>();

        boardAdapter = new BoardAdapter(R.layout.grid_pin, pins);

        RecyclerView boardView = (RecyclerView) findViewById(R.id.board);
        boardView.setHasFixedSize(true);
        boardView.setLayoutManager(gridLayoutManager);
        boardView.setAdapter(boardAdapter);
    }

    public void fetchPins()
    {
        String result = DataCache.getInstance().getFromCache(URL);

        if(result == null)
        {
            UniversalDownloader downloader = new UniversalDownloader();
            downloader.downloadData(URL, progress, callback);
        }
        else
        {
            callback.onSuccess(result);
        }

    }

    private UniversalDownloader.Callback callback = new UniversalDownloader.Callback()
    {
        @Override
        public void onSuccess(String result) {
            Log.v("Callback success", result);

            JsonParser parser = new JsonParser();

            List<Pin> pins = parser.parsePins(result);

            if(pins != null)
            {
                MainActivity.this.pins.addAll(pins);
                boardAdapter.notifyDataSetChanged();
            }
            else
            {
                Snackbar.make(findViewById(android.R.id.content),
                              "Something went wrong while fetching pins",
                               Snackbar.LENGTH_INDEFINITE)
                               .setAction("Retry", new View.OnClickListener()
                               {
                                   @Override
                                   public void onClick(View view) {
                                       fetchPins();
                                   }
                               }).show();
            }
        }

        @Override
        public void onError(Exception e)
        {
           Snackbar.make(findViewById(android.R.id.content), "Network Error",
                Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    fetchPins();
                }
            }).show();
        }
    };

    @Override
    protected void onDestroy()
    {

        ImageCache.getInstance().tearDown();
        DataCache.getInstance().tearDown();

        super.onDestroy();
    }
}