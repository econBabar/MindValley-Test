import android.test.ActivityUnitTestCase;

import junit.framework.TestCase;

import org.junit.Test;

import test.utils.DataCache;
import test.utils.ImageCache;
import static org.junit.Assert.*;

/**
 * Created by Babar on 24-Jul-16.
 */
public class CacheTest extends TestCase
{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testInitialization()
    {
        ImageCache imageCache = ImageCache.getInstance();
        DataCache dataCache = DataCache.getInstance();

        assertNotNull("Image Cache is null", imageCache);
        assertNotNull("Data Cache is null", dataCache);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}

