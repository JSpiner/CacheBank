package net.jspiner.cachebank.model;

import android.support.annotation.Nullable;

import io.reactivex.Observable;

/**
 * Created by JSpiner on 2017. 7. 14..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public class FoodModel{

    public int index;
    public String foodName;

    public FoodModel(){

    }

    public FoodModel(int index, String foodName){
        this.index = index;
        this.foodName = foodName;
    }
}
