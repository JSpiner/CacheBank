package net.jspiner.cachebank;

import net.jspiner.cachebank.model.CarModel;
import net.jspiner.cachebank.model.FoodModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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
        Bank.put(new CarModel(9986, "avante-new"), "avante");

        Thread.sleep(1000 * 2);

        CarModel carModel = Bank.getNow(CarModel.class, "avante");

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
        Bank.put(new CarModel(9986, "avante-new"), "avante");

        Thread.sleep(500);

        CarModel carModel = Bank.getNow(CarModel.class, "avante");

        Assert.assertEquals(
                carModel.index,
                9986
        );
        Assert.assertEquals(
                carModel.carName,
                "avante-new"
        );

    }

    @Test
    public void observableExpireTest() throws InterruptedException {
        Bank.put(new FoodModel(5555, "hamburger"), "burger");
        Bank.get(FoodModel.class, "burger").subscribe(
                foodModel -> {

                }
        );

        Thread.sleep(1000 * 10);

        Bank.get(FoodModel.class, "burger").subscribe(
                foodModel -> {
                    Assert.assertEquals(foodModel.index, 2311);
                    Assert.assertEquals(foodModel.foodName, "burger");
                }
        );

    }


}
