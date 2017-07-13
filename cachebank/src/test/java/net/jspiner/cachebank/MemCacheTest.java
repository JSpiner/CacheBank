package net.jspiner.cachebank;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public class MemCacheTest {

    @Before
    public void before(){
        new Bank.Builder()
                .setMemCacheSize(1000 * 24)
                .init();

        Assert.assertTrue(Bank.isInitialized());
    }

    @After
    public void tearDown(){
        Bank.clear();
        Bank.terminate();

        Assert.assertFalse(Bank.isInitialized());
    }

    @Test
    public void dataLoadTest(){
        Bank.put("genesis", new CarModel(9987, "genesis"));
        CarModel cachedData = Bank.get("genesis", CarModel.class);

        Assert.assertEquals("genesis", cachedData.carName);
        Assert.assertEquals(9987, cachedData.index);
    }

    @Test
    public void cacheTimeInTest(){
        Bank.put("avante", new CarModel(9986, "avante-new"));
        CarModel cachedData = Bank.get("avante", CarModel.class);

        Assert.assertEquals("avante-new", cachedData.carName);
        Assert.assertEquals(9986, cachedData.index);
    }

    @Test
    public void cacheTimeOutTest() throws Exception{
        Bank.put("avante", new CarModel(9986, "avante-new"));
        Thread.sleep(1000);
        CarModel cachedData = Bank.get("avante", CarModel.class);

        Assert.assertEquals("avante", cachedData.carName);
        Assert.assertEquals(1256, cachedData.index);
    }

}
