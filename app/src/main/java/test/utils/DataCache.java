package test.utils;

import android.util.LruCache;

/**
 * Lru based cache to cache text based data. Follows Singleton pattern.
 */
public class DataCache
{
    private static DataCache dataCache;

    private static LruCache<String, String> lruCache;

    private DataCache(){};

    public static DataCache getInstance()
    {
        if(dataCache == null)
        {
            dataCache = new DataCache();
        }

        return dataCache;
    }

    /**
     * Initialize the cache
     * @param numPages the num of text based data that this cache can hold.
     *                 This is specifically the number of String objects that
     *                 the cache can save.
     */
    public static void init(int numPages)
    {
        lruCache = new LruCache<>(numPages);
    }

    /**
     * Adds to cache
     * @param key key for this cache entry
     * @param value the string to save
     */
    public void addToCache(String key, String value)
    {
        if(getFromCache(key) == null)
        {
            lruCache.put(key, value);
        }
    }

    /**
     * Gets from cache
     * @param key the key of the required entry
     * @return data as String
     */
    public String getFromCache(String key)
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
