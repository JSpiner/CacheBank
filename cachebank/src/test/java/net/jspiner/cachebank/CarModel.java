package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public class CarModel implements ProviderInterface<CarModel> {

    public int index;
    public String carName;

    public CarModel(){

    }

    public CarModel(int index, String carName){
        this.index = index;
        this.carName = carName;
    }

    @Override
    public CarModel initData() {
        return new CarModel(
                1245,
                "sonata"
        );
    }

    @Override
    public CarModel updateData(CarModel prevData) {
        return new CarModel(
                5421,
                "avante"
        );
    }
}
