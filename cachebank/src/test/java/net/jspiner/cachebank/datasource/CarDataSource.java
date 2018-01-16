package net.jspiner.cachebank.datasource;

import net.jspiner.cachebank.DataEmitter;
import net.jspiner.cachebank.DataSource;
import net.jspiner.cachebank.DummyNetwork;
import net.jspiner.cachebank.model.CarModel;

/**
 * Created by prnd on 2018. 1. 16..
 */

public class CarDataSource implements DataSource<String, CarModel> {

    @Override
    public void fetchData(String key, DataEmitter<CarModel> emitter) {
        emitter.emit(
                DummyNetwork.requestCar(key)
        );
    }

}
