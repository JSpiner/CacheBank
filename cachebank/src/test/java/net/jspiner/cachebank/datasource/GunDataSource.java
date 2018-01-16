package net.jspiner.cachebank.datasource;

import net.jspiner.cachebank.DataEmitter;
import net.jspiner.cachebank.DataSource;
import net.jspiner.cachebank.DummyNetwork;
import net.jspiner.cachebank.model.GunModel;

/**
 * Created by prnd on 2018. 1. 16..
 */

public class GunDataSource implements DataSource<String, GunModel> {

    @Override
    public void fetchData(String key, DataEmitter<GunModel> emitter) {
        emitter.emit(null);
    }
}
