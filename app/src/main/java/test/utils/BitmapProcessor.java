package test.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import test.asynctasks.ImageDownloadTask;

public class BitmapProcessor
{
    /**
     * Decodes bitmap from {@link ByteArrayOutputStream} by sampling to required estimated
     * width, height.
     * @param downloadTask the instance of the asynctask
     * @param baos the {@link ByteArrayOutputStream} to decode the bitmap
     * @param url source url to download the bitmap as stream
     * @param reqWidth estimated required width
     * @param reqHeight estimated required height
     * @return bitmap or null if it can't be download for some reason
     * @throws IOException
     */
    public static Bitmap decodeSampledBitmapFromStream(ImageDownloadTask downloadTask,
                                                       ByteArrayOutputStream baos, URL url,
                                                       int reqWidth, int reqHeight) throws IOException {
        Bitmap bitmap;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size(), options);

       // BitmapFactory.decodeStream(inputStream, null, options);

        int sampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;

        InputStream inputStream = url.openStream();

        baos = downloadTask.readInChunks(inputStream);

        bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size(), options);
        //bitmap = BitmapFactory.decodeStream(inputStream, null, options);

        baos.close();

        return bitmap;
    }

    /**
     * Calculates the sample size to downsize the bitmap.
     * @param options the options containing information regarding bitmap
     * @param requiredWidth estimated required width
     * @param requiredHeight estimated required height
     * @return sample size
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                     int requiredWidth, int requiredHeight)
    {
        final int width = options.outWidth;
        final int height = options.outHeight;

        int inSampleSize = 1;

        if(width > requiredWidth || height > requiredHeight)
        {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfWidth / inSampleSize) > requiredWidth
                                    ||
                    (halfHeight / inSampleSize) > requiredHeight)
            {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
