package net.jspiner.cachebank;

import net.jspiner.cachebank.model.AnimalModel;
import net.jspiner.cachebank.model.CarModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by JSpiner on 2017. 7. 14..
 * PRNDCompany
 * Contact : smith@prnd.co.kr
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
            Bank.get("sonata", FoodModel.class);
        }catch (Exception e){
            Assert.assertEquals(ClassCastException.class, e.getClass());
        }
    }

    @Test
    public void instantiationExceptionTest(){
        try {
            Bank.get("monky", AnimalModel.class);
        } catch (Exception e){
            Assert.assertEquals(InstantiationException.class, e.getCause().getClass());
        }

    }

    @Test
    public void instantiationExceptionTest2(){
        Bank.put("monky", new AnimalModel(3123, "monkey"));
        try {
            Thread.sleep(1000);
            Bank.get("monky", AnimalModel.class);
        } catch (Exception e){
            Assert.assertEquals(InstantiationException.class, e.getCause().getClass());
        }

    }
}
