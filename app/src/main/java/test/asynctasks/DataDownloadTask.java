package test.asynctasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import test.utils.DownloadTaskWarehouse;
import test.utils.DataCache;
import test.utils.UniversalDownloader;

/**
 * Downloads json data
 */
public class DataDownloadTask extends AsyncTask<String, Void, String>
{
    private String key;

    private ProgressBar progressBar;

    private UniversalDownloader.Callback callback;

    public final static int TIME_OUT = 30 * 1000;

    public final static String METHOD = "GET";

    protected final static String ENCODING = "UTF-8";

    public DataDownloadTask(String key, ProgressBar progressBar, UniversalDownloader.Callback callback)
    {
        this.key = key;

        this.progressBar = progressBar;

        this.callback = callback;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        if(progressBar != null) {progressBar.setVisibility(View.VISIBLE);}
    }

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            DownloadTaskWarehouse.getInstance().add(key, this);

            return download(params[0]);
        }
        catch (IOException e)
        {
            e.printStackTrace();

            if(callback != null) callback.onError(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);

        if(progressBar != null) {progressBar.setVisibility(View.GONE);}

        DownloadTaskWarehouse.getInstance().remove(key);

        if(result != null)
        {
            DataCache.getInstance().addToCache(key, result);

            if(callback != null) callback.onSuccess(result);
        }
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();

        DownloadTaskWarehouse.getInstance().remove(key);
    }

    private String download(String jsonUrl) throws IOException
    {
        BufferedReader reader = null;

        try
        {
            URL url = new URL(jsonUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(TIME_OUT);
            connection.setConnectTimeout(TIME_OUT);
            connection.setRequestMethod(METHOD);

            InputStream is = connection.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is, ENCODING));
            String line;
            while((line = reader.readLine()) != null)
            {
                if(isCancelled())
                {
                    return null;
                }

                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        }
        finally
        {
            if(reader != null)
            {
                reader.close();
            }
        }
    }
}