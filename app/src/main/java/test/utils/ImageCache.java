package test.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * LRU based image cache following Singleton pattern
 */
public class ImageCache
{
    private static ImageCache imageCache;

    private static LruCache<String, Bitmap> lruCache;

    private ImageCache(){};

    public static ImageCache getInstance()
    {
        if(imageCache == null)
        {
            imageCache = new ImageCache();
        }

        return imageCache;
    }

    /**
     * Initialize the cache
     * @param cacheRatio the part of the cache from total available memory
     */
    public void init(int cacheRatio)
    {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) Runtime.getRuntime().maxMemory();

        final int cacheSize = maxMemory / cacheRatio;

        Log.i("maxHeapMemory: ", "" + maxMemory / 1024 / 1024 + " MB");
        Log.i("CacheSize: ",""+cacheSize / 1024/ 1024 + " MB");

        lruCache = new LruCache<String, Bitmap>(cacheSize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                // The cache size will be measured in kilobytes rather than
                // number of items.

                return value.getByteCount() / 1024;
            }
        };
    }

    /**
     * Adds bitmap to cache
     * @param key the key of the bitmap
     * @param value bitmap
     */
    public void addBitmapToCache(String key, Bitmap value)
    {
        if(getBitmapFromCache(key) == null)
        {
            lruCache.put(key, value);
        }
    }

    /**
     * Gets bitmap from cache
     * @param key the key of the bitmap
     * @return bitmap
     */
    public Bitmap getBitmapFromCache(String key)
    {
        return lruCache.get(key);
    }

    /**
     * Clear the cache
     */
    public void tearDown()
    {
        lruCache.evictAll();
    }

}
