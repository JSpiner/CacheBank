package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2018. 1. 16..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public interface DataSource<T> {

    void fetchData(DataEmitter<T> emitter);

}
