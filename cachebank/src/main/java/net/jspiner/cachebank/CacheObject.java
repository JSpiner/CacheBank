package net.jspiner.cachebank;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by JSpiner on 2017. 7. 13..
 * JSpiner
 * Contact : jspiner@naver.com
 */

final class CacheObject<T extends ProviderInterface> {

    private long expireTime;
    private String key;
    private T value;
    private Observable<T> valueObservable;
    private boolean isObservable;

    protected CacheObject(String key, T value, long expireTime){
        this.key = key;
        this.value = value;
        this.expireTime = expireTime;
    }

    protected static <T extends ProviderInterface> CacheObject newInstance(Class<T> targetClass, String key){
        T dataInstance = getTargetInstance(targetClass);
        T fetchedInstance = (T)dataInstance.fetchData(key, null);

        if(fetchedInstance==null){
            fetchedInstance = dataInstance;
        }

        CacheObject cacheObject = new CacheObject<T>(
                key,
                fetchedInstance,
                fetchedInstance.getCacheTime()
        );
        return cacheObject;
    }

    private static <T> T getTargetInstance(Class<T> targetClass){
        try {
            return targetClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("DataModel must have default constructor()", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException", e);
        }
    }

    protected void update(String key){
        Observable<T> fetchObservable = value.fetchDataObservable(key, value);

        if(fetchObservable != null){
            isObservable = true;
            this.valueObservable = fetchObservable;
        }
        else{
            isObservable = false;
            T fetchData = (T)value.fetchData(key, value);
            value = fetchData;
        }
    }

    protected Observable getValueObservable(){
        return valueObservable;
    }

    protected boolean isObservable(){
        return isObservable;
    }

    protected long getExpireTime(){
        return expireTime;
    }

    protected String getKey() {
        return key;
    }

    protected T getValue() {
        return value;
    }
}
