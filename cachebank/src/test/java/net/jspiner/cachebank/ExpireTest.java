package net.jspiner.cachebank;

import net.jspiner.cachebank.model.CarModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 14..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public class ExpireTest {

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
    public void expireTest() throws InterruptedException {
        Bank.put("avante", new CarModel(9986, "avante-new"));

        Thread.sleep(1000 * 2);

        CarModel carModel = Bank.getNow("avante", CarModel.class);

        Assert.assertEquals(
                carModel.index,
                1256
        );
        Assert.assertEquals(
                carModel.carName,
                "avante"
        );

    }

    @Test
    public void notExpireTest() throws InterruptedException {
        Bank.put("avante", new CarModel(9986, "avante-new"));

        Thread.sleep(500);

        CarModel carModel = Bank.getNow("avante", CarModel.class);

        Assert.assertEquals(
                carModel.index,
                9986
        );
        Assert.assertEquals(
                carModel.carName,
                "avante-new"
        );

    }


}
