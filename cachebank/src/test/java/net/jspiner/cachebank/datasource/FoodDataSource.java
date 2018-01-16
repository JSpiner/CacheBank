package net.jspiner.cachebank.datasource;

import net.jspiner.cachebank.DataEmitter;
import net.jspiner.cachebank.DataSource;
import net.jspiner.cachebank.DummyNetwork;
import net.jspiner.cachebank.model.FoodModel;

/**
 * Created by prnd on 2018. 1. 16..
 */

public class FoodDataSource implements DataSource<String, FoodModel> {
    @Override
    public void fetchData(String key, DataEmitter<FoodModel> emitter) {
        DummyNetwork.requestFoodObservable(key).subscribe(
                foodModel -> emitter.emit(foodModel)
        );
    }
}
