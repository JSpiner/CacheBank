package net.jspiner.cachebank;

import android.support.annotation.Nullable;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
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
