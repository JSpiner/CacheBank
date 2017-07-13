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
    public void test1(){
        Bank.get("key", CarModel.class);
    }

}
