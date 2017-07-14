package net.jspiner.cachebank;

import net.jspiner.cachebank.model.CarModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@prnd.co.kr
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
        Object object = Bank.get("sonata", CarModel.class);
        Assert.assertEquals(object.getClass(), CarModel.class);
    }

    @Test
    public void emptyDataObjectReturnTest(){
        Object object = Bank.get("garbage", CarModel.class);
        Assert.assertNull(object);
    }

    @Test
    public void dataReturnValueTest(){
        CarModel cachedData = Bank.get("sonata", CarModel.class);

        Assert.assertEquals("sonata", cachedData.carName);
        Assert.assertEquals(1255, cachedData.index);
    }

    @Test
    public void dataUpdateReturnValueTest(){
        CarModel cachedData;
        cachedData = Bank.get("sonata", CarModel.class);
        cachedData = Bank.get("avante", CarModel.class);

        Assert.assertEquals("avante", cachedData.carName);
        Assert.assertEquals(1256, cachedData.index);

    }

}
