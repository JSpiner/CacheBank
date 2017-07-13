package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public class CarModel extends Provider<CarModel> {

    public int index;
    public String carName;

    public CarModel(int index, String carName){
        this.index = index;
        this.carName = carName;
    }

    @Override
    public int getCacheTime() {
        return 1000;
    }

    @Override
    public CarModel fetchData(String key, CarModel prevData) {
        return DummyNetwork.request(key);
    }
}
