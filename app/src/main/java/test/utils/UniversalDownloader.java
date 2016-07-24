package test.utils;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import test.asynctasks.DataDownloadTask;
import test.asynctasks.ImageDownloadTask;

/**
 * Basic download operations, Currently supports Image and Json only.
 */
public class UniversalDownloader
{
    /**
     * Download images over Http/Https.
     * @param url a url of the content
     * @param reqWidth the estimated desired width of the image to download
     * @param reqHeight the estimated desired height of the image to download
     * @param imageView the ImageView to set image on
     */
    public void downloadAndSetImage(String url, int reqWidth, int reqHeight,
                              ImageView imageView, String key)
    {
        if(imageView == null)
        {
            throw new NullPointerException("imageView must not be null");
        }

        if(url == null)
        {
            throw new NullPointerException("url can't be null");
        }

        if(DownloadTaskWarehouse.getInstance().get(key) == null)
        {
            imageView.setTag(key);

            ImageDownloadTask downloadTask = new ImageDownloadTask(imageView, key);
            downloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url,
                    String.valueOf(reqWidth),
                    String.valueOf(reqHeight));
        }
        else
        {
            Log.v("downloadAndSetImage", "InProgress | Skipping " + key);
        }
    }

    /**
     * A method to download text based data (JSON, XML etc).
     * @param url the url to download the data
     * @param callback responsible for returning response back to caller
     */
    public void downloadData(String url, @Nullable ProgressBar progress, Callback callback)
    {
        if(url == null)
        {
            throw new NullPointerException("url can't be null");
        }

        if(DownloadTaskWarehouse.getInstance().get(url) == null)
        {
            DataDownloadTask downloadTask = new DataDownloadTask(url, progress, callback);
            downloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
        }
        else
        {
            Log.v("downloadData", "InProgress | Skipping " + url);
        }
    }

    /**
     * Attempt to cancel the task only if it was pending
     * (i.e in queue) or in running state
     * @param key the key that was passed at the time of download
     */
    public void cancel(String key)
    {
        AsyncTask asyncTask = DownloadTaskWarehouse.getInstance().get(key);

        if(asyncTask != null &&
          (asyncTask.getStatus() == AsyncTask.Status.PENDING ||
           asyncTask.getStatus() == AsyncTask.Status.RUNNING))
        {
            asyncTask.cancel(true);
        }
    }


    public interface Callback
    {
        void onSuccess(String result);

        void onError(Exception e);
    }
}

