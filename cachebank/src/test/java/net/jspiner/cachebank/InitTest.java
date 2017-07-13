package net.jspiner.cachebank;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public class InitTest {

    @After
    public void tearDown(){
        Bank.clear();
        Bank.terminate();
    }

    @Test
    public void builderInitTest(){
        Bank.Builder.init();

        Assert.assertTrue(Bank.isInitialized());
    }

    @Test
    public void builderInitWithMemSizeTest(){
        int memCacheSize = 100;
        Bank.Builder.init()
                .setMemCacheSize(memCacheSize);


        Assert.assertTrue(Bank.isInitialized());
        Assert.assertEquals(memCacheSize, Bank.getMemCacheSize());
    }

    @Test
    public void builderInitwithDiskSizeTest(){
        int diskCacheSize = 100;
        Bank.Builder.init()
                .setDiskCacheSize(diskCacheSize);

        Assert.assertTrue(Bank.isInitialized());
        Assert.assertEquals(diskCacheSize, Bank.getDiskCacheSize());

    }

    @Test
    public void builderInitWithMemDiskSizeTest(){
        int memCacheSize = 100;
        int diskCacheSize = 100;

        Bank.Builder.init()
                .setMemCacheSize(100)
                .setDiskCacheSize(100);

        Assert.assertTrue(Bank.isInitialized());
        Assert.assertEquals(memCacheSize, Bank.getMemCacheSize());
        Assert.assertEquals(diskCacheSize, Bank.getDiskCacheSize());
    }



}
