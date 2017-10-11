package net.jspiner.cachebank;

import net.jspiner.cachebank.model.CarModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 13..
 * JSpiner
 * Contact : jspiner@naver.com
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
        Bank.put(new CarModel(9987, "genesis"), "genesis");
        CarModel cachedData = Bank.getNow(CarModel.class, "genesis");

        Assert.assertEquals(cachedData.carName, "genesis");
        Assert.assertEquals(9987, cachedData.index);
    }

    @Test
    public void cacheTimeInTest(){
        Bank.put(new CarModel(9986, "avante-new"), "avante");
        CarModel cachedData = Bank.getNow(CarModel.class, "avante");

        Assert.assertEquals("avante-new", cachedData.carName);
        Assert.assertEquals(9986, cachedData.index);
    }

    @Test
    public void cacheTimeInTest2() throws Exception{
        Bank.put(new CarModel(9986, "avante-new"), "avante");
        Thread.sleep(500);
        CarModel cachedData = Bank.getNow(CarModel.class, "avante");

        Assert.assertEquals("avante-new", cachedData.carName);
        Assert.assertEquals(9986, cachedData.index);
    }

    @Test
    public void cacheTimeOutTest() throws Exception{
        Bank.put(new CarModel(9986, "avante-new"), "avante");
        Thread.sleep(2000);
        CarModel cachedData = Bank.getNow(CarModel.class, "avante");

        Assert.assertEquals("avante", cachedData.carName);
        Assert.assertEquals(1256, cachedData.index);
    }

    @Test
    public void cacheTimeOutTest2() throws Exception{
        Bank.put(new CarModel(9986, "avante-new"), "avante");
        Thread.sleep(10000);
        CarModel cachedData = Bank.getNow(CarModel.class, "avante");

        Assert.assertEquals("avante", cachedData.carName);
        Assert.assertEquals(1256, cachedData.index);

    }

    @Test
    public void dataUpdateTest(){
        Bank.put(new CarModel(9988, "sonata-old"), "sonata");
        Bank.put(new CarModel(9989, "sonata-new"), "sonata");
        CarModel cachedData = Bank.getNow(CarModel.class, "sonata");

        Assert.assertEquals("sonata-new", cachedData.carName);
        Assert.assertEquals(9989, cachedData.index);
    }

}
