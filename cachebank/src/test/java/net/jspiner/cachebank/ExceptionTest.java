package net.jspiner.cachebank;

import net.jspiner.cachebank.model.AnimalModel;
import net.jspiner.cachebank.model.CarModel;
import net.jspiner.cachebank.model.FoodModel;
import net.jspiner.cachebank.model.GunModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 14..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public class ExceptionTest {

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
    public void classCastExceptionTest(){
        Bank.put("sonata", new CarModel(1933, "sonata-new"));
        try {
            Bank.getNow("sonata", FoodModel.class);
        }catch (Exception e){
            Assert.assertEquals(ClassCastException.class, e.getClass());

            return;
        }
        Assert.fail();
    }

    @Test
    public void instantiationExceptionTest(){
        try {
            Bank.getNow("monky", AnimalModel.class);
        } catch (Exception e){
            Assert.assertEquals(InstantiationException.class, e.getCause().getClass());
            return;
        }

        Assert.fail();
    }

    @Test
    public void illegalAccessExceptionTest(){
        try {
            Bank.getNow("bereta", GunModel.class);
        } catch (Exception e){
            Assert.assertEquals(IllegalAccessException.class, e.getCause().getClass());

            return;
        }

        Assert.fail();
    }

}
