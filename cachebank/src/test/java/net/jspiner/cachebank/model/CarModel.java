package net.jspiner.cachebank.model;

import android.support.annotation.Nullable;

import net.jspiner.cachebank.DummyNetwork;
import net.jspiner.cachebank.Provider;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@prnd.co.kr
 */

public class CarModel extends Provider<CarModel> {

    public int index;
    public String carName;

    public CarModel(){

    }

    public CarModel(int index, String carName){
        this.index = index;
        this.carName = carName;
    }

    @Override
    public int getCacheTime() {
        return 1000;
    }

    @Override
    public CarModel fetchData(String key, @Nullable CarModel prevData) {
        return DummyNetwork.requestCar(key);
    }
}
