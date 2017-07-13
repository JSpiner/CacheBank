package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public class CarModel implements ProviderInterface<CarModel> {

    public int index;
    public String carName;

    @Override
    public CarModel initData() {
        return null;
    }

    @Override
    public CarModel updateData(CarModel prevData) {
        return null;
    }
}
