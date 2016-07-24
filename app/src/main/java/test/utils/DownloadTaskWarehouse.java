package test.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

/**
 * A helper class for managing the running and finished tasks
 * Follows Singleton pattern.
 */
public class DownloadTaskWarehouse
{
    private static DownloadTaskWarehouse downloadTaskWarehouse;

    private static HashMap<String, AsyncTask> downloadsMap;

    private DownloadTaskWarehouse(){}

    public static DownloadTaskWarehouse getInstance()
    {
        if(downloadTaskWarehouse == null)
        {
            downloadTaskWarehouse = new DownloadTaskWarehouse();

            downloadsMap = new HashMap<>();
        }

        return downloadTaskWarehouse;
    }

    /**
     * Adds task to warehouse. Whenever a task is started
     * it must be added to warehouse to avoid duplicate runs.
     * @param key unique key of the download
     * @param asyncTask task
     */
    public void add(String key, AsyncTask asyncTask)
    {
        Log.v("DownloadTaskWarehouse", "Added " + key);
        downloadsMap.put(key, asyncTask);
    }

    /**
     * Fetch the task associated with key
     * @param key unique key of the download
     * @return task
     */
    public AsyncTask get(String key)
    {
        return downloadsMap.get(key);
    }

    /**
     * Remove the task from warehouse. Whenever a task is finished
     * or cancelled it must be remove from warehouse.
     * @param key
     */
    public void remove(String key)
    { Log.v("DownloadTaskWarehouse", "Removed " + key);
        downloadsMap.remove(key);
    }
}
