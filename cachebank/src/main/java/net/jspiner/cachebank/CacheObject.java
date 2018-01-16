package net.jspiner.cachebank;

import io.reactivex.Observable;

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
        T dataInstance = getTargetInstance(targetClass);

        // TODO : Provider가 바뀌면서 cache time이 default로 임시 변경됨. 추후 재변경 필요
        CacheObject cacheObject = new CacheObject<K, T>(
                key,
                dataInstance,
                BankConstant.DEFAULT_CACHE_TIME
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

    protected void update(K key){
        /*
        Observable<T> fetchObservable = value.fetchDataObservable(key, value);

        if(fetchObservable != null){
            isObservable = true;
            this.valueObservable = fetchObservable;
        }
        else{
            isObservable = false;
            T fetchData = (T)value.fetchData(key, value);
            value = fetchData;
        }*/
    }

    protected Observable getValueObservable(){
        if(valueObservable == null){
            return Observable.create(
                    emitter -> emitter.onNext(value)
            );
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
