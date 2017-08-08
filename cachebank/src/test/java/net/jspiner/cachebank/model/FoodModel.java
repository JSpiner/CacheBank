package net.jspiner.cachebank.model;

import android.support.annotation.Nullable;

import net.jspiner.cachebank.DummyNetwork;
import net.jspiner.cachebank.Provider;

import io.reactivex.Observable;

/**
 * Created by JSpiner on 2017. 7. 14..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public class FoodModel extends Provider<FoodModel> {

    public int index;
    public String foodName;

    public FoodModel(){

    }

    public FoodModel(int index, String foodName){
        this.index = index;
        this.foodName = foodName;
    }

    @Override
    public Observable<FoodModel> fetchDataObservable(String key, @Nullable FoodModel prevData) {
        return super.fetchDataObservable(key, prevData);
    }
}
