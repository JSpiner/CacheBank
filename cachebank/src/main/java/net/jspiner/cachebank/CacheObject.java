package net.jspiner.cachebank;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by JSpiner on 2017. 7. 13..
 * JSpiner
 * Contact : jspiner@naver.com
 */

final class CacheObject<K, T> {

    private long expireTime;
    private K key;
    private T value;
    private Observable<T> valueObservable;
    private boolean isObservable;

    protected CacheObject(K key, T value, long expireTime){
        this.key = key;
        this.value = value;
        this.expireTime = expireTime;
    }

    protected static <T, K> CacheObject newInstance(Class<T> targetClass, K key){
        CacheObject cacheObject = new CacheObject<K, T>(
                key,
                null,
                BankConstant.DEFAULT_CACHE_TIME
        );
        return cacheObject;
    }

    protected void update(K key, DataSource<K, T> dataSource){
        dataSource.fetchData(
                key,
                data -> {
                    isObservable = true;
                    valueObservable = Observable.just(data);
                }
        );
    }

    protected Observable getValueObservable(){
        if(valueObservable == null){
            if(value == null){
                return Observable.empty();
            }
            return Observable.just(value);
        }
        return valueObservable;
    }

    protected boolean isObservable(){
        return isObservable;
    }

    protected long getExpireTime(){
        return expireTime;
    }

    protected K getKey() {
        return key;
    }

    protected T getValue() {
        return value;
    }
}
