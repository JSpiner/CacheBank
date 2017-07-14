package net.jspiner.cachebank;

import android.support.annotation.Nullable;

/**
 * Created by JSpiner on 2017. 7. 14..
 * PRNDCompany
 * Contact : smith@gmail.com
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
    public FoodModel fetchData(String key, @Nullable FoodModel prevData) {
        return DummyNetwork.requestFood(key);
    }

}
