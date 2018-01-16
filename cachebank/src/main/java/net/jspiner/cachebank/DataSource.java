package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2018. 1. 16..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public interface DataSource<K, T> {

    void fetchData(K key, DataEmitter<T> emitter);

}
