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

        Assert.assertFalse(Bank.isInitialized());
    }

    @Test
    public void builderInitTest(){
        new Bank.Builder().init();

        Assert.assertTrue(Bank.isInitialized());
    }

    @Test
    public void builderInitWithMemSizeTest(){
        int memCacheSize = 100;
        new Bank.Builder()
                .setMemCacheSize(memCacheSize)
                .init();


        Assert.assertTrue(Bank.isInitialized());
        Assert.assertEquals(memCacheSize, Bank.getMemCacheSize());
    }

    @Test
    public void builderInitwithDiskSizeTest(){
        int diskCacheSize = 100;
        new Bank.Builder()
                .setDiskCacheSize(diskCacheSize)
                .init();

        Assert.assertTrue(Bank.isInitialized());
        Assert.assertEquals(diskCacheSize, Bank.getDiskCacheSize());

    }

    @Test
    public void builderInitWithMemDiskSizeTest(){
        int memCacheSize = 100;
        int diskCacheSize = 100;

        new Bank.Builder()
                .setMemCacheSize(100)
                .setDiskCacheSize(100)
                .init();

        Assert.assertTrue(Bank.isInitialized());
        Assert.assertEquals(memCacheSize, Bank.getMemCacheSize());
        Assert.assertEquals(diskCacheSize, Bank.getDiskCacheSize());
    }



}
