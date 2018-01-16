package net.jspiner.cachebank;

import net.jspiner.cachebank.datasource.CarDataSource;
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
        Bank.deposit(new CarModel(9987, "genesis"), "genesis").now();
        CarModel cachedData = Bank.withdrawal(CarModel.class, "genesis")
                .dataSource(new CarDataSource()).now();

        Assert.assertEquals(cachedData.carName, "genesis");
        Assert.assertEquals(9987, cachedData.index);
    }

    @Test
    public void cacheTimeInTest(){
        Bank.deposit(new CarModel(9986, "avante-new"), "avante").now();
        CarModel cachedData = Bank.withdrawal(CarModel.class, "avante")
                .dataSource(new CarDataSource()).now();

        Assert.assertEquals("avante-new", cachedData.carName);
        Assert.assertEquals(9986, cachedData.index);
    }

    @Test
    public void cacheTimeInTest2() throws Exception{
        Bank.deposit(new CarModel(9986, "avante-new"), "avante").now();
        Thread.sleep(500);
        CarModel cachedData = Bank.withdrawal(CarModel.class, "avante")
                .dataSource(new CarDataSource()).now();

        Assert.assertEquals("avante-new", cachedData.carName);
        Assert.assertEquals(9986, cachedData.index);
    }

    @Test
    public void cacheTimeOutTest() throws Exception{
        Bank.deposit(new CarModel(9986, "avante-new"), "avante").now();
        Thread.sleep(2000);
        CarModel cachedData = Bank.withdrawal(CarModel.class, "avante")
                .dataSource(new CarDataSource()).now();

        Assert.assertEquals("avante", cachedData.carName);
        Assert.assertEquals(1256, cachedData.index);
    }

    @Test
    public void cacheTimeOutTest2() throws Exception{
        Bank.deposit(new CarModel(9986, "avante-new"), "avante").now();
        Thread.sleep(10000);
        CarModel cachedData = Bank.withdrawal(CarModel.class, "avante")
                .dataSource(new CarDataSource()).now();

        Assert.assertEquals("avante", cachedData.carName);
        Assert.assertEquals(1256, cachedData.index);

    }

    @Test
    public void dataUpdateTest(){
        Bank.deposit(new CarModel(9988, "sonata-old"), "sonata").now();
        Bank.deposit(new CarModel(9989, "sonata-new"), "sonata").now();
        CarModel cachedData = Bank.withdrawal(CarModel.class, "sonata")
                .dataSource(new CarDataSource()).now();

        Assert.assertEquals("sonata-new", cachedData.carName);
        Assert.assertEquals(9989, cachedData.index);
    }

    @Test
    public void dataDepositTest(){
        Bank.deposit(
                new CarModel(9987, "genesis"),
                "genesis"
        ).now();
        CarModel cachedData = Bank.withdrawal(CarModel.class, "genesis")
                .dataSource(new CarDataSource()).now();

        Assert.assertEquals(cachedData.carName, "genesis");
        Assert.assertEquals(9987, cachedData.index);
    }

}
