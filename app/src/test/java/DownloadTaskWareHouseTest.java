import junit.framework.TestCase;

import test.utils.DownloadTaskWarehouse;

/**
 * Created by Babar on 24-Jul-16.
 */
public class DownloadTaskWareHouseTest extends TestCase
{
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testInitialization()
    {
        DownloadTaskWarehouse warehouse = DownloadTaskWarehouse.getInstance();

        assertNotNull("warehouse is null", warehouse);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
