package net.jspiner.cachebank;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public class ProviderTest {

    @Before
    public void before(){
        new Bank.Builder()
                .init();

        Assert.assertTrue(Bank.isInitialized());
    }

    @Test
    public void getDataObjectReturnClassTypeTest(){
        Object object = Bank.get("key", CarModel.class);
        Assert.assertEquals(object.getClass(), CarModel.class);
    }

    @Test
    public void checkDataReturnValueTest(){
        CarModel cachedData = Bank.get("key", CarModel.class);

        Assert.assertEquals("sonata", cachedData.carName);
        Assert.assertEquals(1254, cachedData.index);
    }

    @Test
    public void putDataObjectTest(){
        Bank.put("key", new CarModel());

        
    }

}
