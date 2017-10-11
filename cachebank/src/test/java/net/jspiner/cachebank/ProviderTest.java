package net.jspiner.cachebank;

import net.jspiner.cachebank.model.CarModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 13..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public class ProviderTest {

    @Before
    public void before(){
        new Bank.Builder()
                .init();

        Assert.assertTrue(Bank.isInitialized());
    }

    @Test
    public void dataObjectReturnClassTypeTest(){
        Object object = Bank.getNow(CarModel.class, "sonata");
        Assert.assertEquals(object.getClass(), CarModel.class);
    }

    @Test
    public void emptyDataObjectReturnTest(){
        Object object = Bank.getNow(CarModel.class, "garbage");
        Assert.assertNull(object);
    }

    @Test
    public void dataReturnValueTest(){
        CarModel cachedData = Bank.getNow(CarModel.class, "sonata");

        Assert.assertEquals("sonata", cachedData.carName);
        Assert.assertEquals(1255, cachedData.index);
    }

    @Test
    public void dataUpdateReturnValueTest(){
        CarModel cachedData;
        cachedData = Bank.getNow(CarModel.class, "sonata");
        cachedData = Bank.getNow(CarModel.class, "avante");

        Assert.assertEquals("avante", cachedData.carName);
        Assert.assertEquals(1256, cachedData.index);

    }

    @Test
    public void provideTest(){
        
    }

}
