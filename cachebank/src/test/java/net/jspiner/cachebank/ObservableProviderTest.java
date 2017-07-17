package net.jspiner.cachebank;

import net.jspiner.cachebank.model.CarModel;
import net.jspiner.cachebank.model.FoodModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableCreate;

/**
 * Created by JSpiner on 2017. 7. 14..
 * PRNDCompany
 * Contact : smith@prnd.co.kr
 */

public class ObservableProviderTest {

    @Before
    public void before(){
        new Bank.Builder()
                .init();

        Assert.assertTrue(Bank.isInitialized());
    }

    @Test
    public void dataObjectReturnClassTypeTest(){
        Object object = Bank.get("pizza", FoodModel.class);
        Assert.assertEquals(ObservableCreate.class, object.getClass());
    }

    @Test
    public void emptyDataObjectReturnTest(){
        Observable<FoodModel> dataObservable = Bank.get("garbage", FoodModel.class);
        dataObservable.subscribe(foodModel -> {
            Assert.assertNull(foodModel);
        });
    }

    @Test
    public void dataReturnValueTest(){
        Observable<FoodModel> dataObservable = Bank.get("pizza", FoodModel.class);

        dataObservable.subscribe(foodModel -> {
            Assert.assertEquals("pizza", foodModel.foodName);
            Assert.assertEquals(2313, foodModel.index);
        });
    }

    @Test
    public void dataUpdateReturnValueTest(){
        Observable<FoodModel> cachedData;
        cachedData = Bank.get("burger", FoodModel.class);
        cachedData = Bank.get("pizza", FoodModel.class);

        cachedData.subscribe(foodModel -> {
            Assert.assertEquals("pizza", foodModel.foodName);
            Assert.assertEquals(2313, foodModel.index);
        });

    }

}
