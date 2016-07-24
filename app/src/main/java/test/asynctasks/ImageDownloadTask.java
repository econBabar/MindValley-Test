package test.asynctasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import test.utils.BitmapProcessor;
import test.utils.DownloadTaskWarehouse;
import test.utils.ImageCache;

/**
 * Downloads images off the UI thread
 */
public class ImageDownloadTask extends AsyncTask<String, Void, Bitmap>
{
    private ImageView imageView;

    private String key;

    private String url;

    public ImageDownloadTask(ImageView imageView, String key)
    {
        this.imageView = imageView;

        this.key = key;
    }

    @Override
    protected Bitmap doInBackground(String... params)
    {
        url = params[0];

        DownloadTaskWarehouse.getInstance().add(key, this);

        return download(url, Integer.parseInt(params[1]),
                        Integer.parseInt(params[2]));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        DownloadTaskWarehouse.getInstance().remove(key);

        super.onPostExecute(bitmap);

        if(bitmap != null)
        {
            ImageCache.getInstance().addBitmapToCache(url, bitmap);

            if(imageView.getTag().equals(key))
            {
                Log.v("ImageDownloadTask", "SetImageBitmap for key: "+key);
                imageView.setImageBitmap(bitmap);
            }
            else
            {
                Log.v("ImageDownloadTask", "Skipping  setImageBitmap for : "+key);
            }
        }
    }

    @Override
    protected void onCancelled()
    {
        super.onCancelled();

        DownloadTaskWarehouse.getInstance().remove(key);
    }

    private Bitmap download(String imageUrl, int reqWidth, int reqHeight)
    {
        try
        {
            URL url = new URL(imageUrl);

            InputStream is = url.openStream();

            ByteArrayOutputStream baos = readInChunks(is);

            return BitmapProcessor.decodeSampledBitmapFromStream(this, baos, url, reqWidth,
                                                                          reqHeight);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Reads the stream in chunks so that it can be cancelled
     * at request
     * @param is the stream to downloads from
     * @return output streams as byte array
     * @throws IOException
     */
    public ByteArrayOutputStream readInChunks(InputStream is) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] byteChunks = new byte[2048];
        int len;
        while ((len = is.read(byteChunks)) != -1)
        {
            if(isCancelled())
            {
                Log.v("ImageDownloadTask", key + " is cancelled");
                return null;
            }

            baos.write(byteChunks, 0, len);
        }

        return baos;
    }
}