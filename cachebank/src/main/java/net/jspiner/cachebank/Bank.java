package net.jspiner.cachebank;

import android.support.v4.util.LruCache;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by JSpiner on 2017. 7. 13..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public final class Bank {

    private static int memCacheSize;
    private static int diskCacheSize;
    private static boolean isInitialized = false;
    private static LruCache lruMemCache = null;
    private static DiskLruCache lruDiskCache = null;
    private static CacheMode cacheMode;

    private Bank(Builder builder){
        this.memCacheSize = builder.memCacheSize;
        this.diskCacheSize = builder.diskCacheSize;
        this.cacheMode = builder.cacheMode;
        this.isInitialized = true;
        this.lruMemCache = new LruCache<String, CacheObject>(memCacheSize);
        //this.lruDiskCache = DiskLruCache.open(null, 1, 2, Integer.MAX_VALUE);
    }

    public static boolean isInitialized(){
        return isInitialized;
    }

    public static <T extends ProviderInterface> Observable<T> get(Class<T> targetClass, String key){
        checkInitAndThrow();

        return Observable.create((ObservableOnSubscribe<CacheObject>)emmiter -> {

            CacheObject<T> cachedObject = getCacheObject(targetClass, key);

            emmiter.onNext(cachedObject);
            emmiter.onComplete();

        }).flatMap((Function<CacheObject, Observable<T>>)cacheObject -> {
            if(cacheObject.isObservable()){
                return cacheObject.getValueObservable();
            }
            return Observable.just((T)cacheObject.getValue());
        });
    }

    public static <T extends ProviderInterface> T getNow(Class<T> targetClass, String key){
        checkInitAndThrow();

        CacheObject<T> cachedObject = getCacheObject(targetClass, key);

        if(cachedObject.isObservable()){
            return (T)cachedObject.getValueObservable().blockingFirst();
        }
        else{
            return cachedObject.getValue();
        }

    }

    private static <T extends ProviderInterface> CacheObject getCacheObject(Class<T> targetClass, String key){
        CacheObject<T> cachedObject = getCacheObjectInCache(targetClass, key);

        boolean isExpired = isExpired(cachedObject);

        if(isExpired){
            cachedObject.update(key);
        }

        return cachedObject;
    }

    private static <T extends ProviderInterface> CacheObject getCacheObjectInCache(Class<T> targetClass, String key){
        CacheObject cachedObject = findInMemory(targetClass, key);

        if(cachedObject == null){
            cachedObject = findInDisk(targetClass, key);

            if(cachedObject != null){
                registerInMemory(cachedObject, key);
            }
        }

        if(cachedObject == null){
            cachedObject = CacheObject.newInstance(targetClass, key);
        }

        return cachedObject;
    }

    private static <T extends ProviderInterface> CacheObject findInMemory(Class<T> targetClass, String key){
        CacheObject<T> cachedObject = (CacheObject<T>) lruMemCache.get(key);
        if(cachedObject == null){
            return null;
        }
        if(!cachedObject.getValue().getClass().equals(targetClass)){
            throw new ClassCastException();
        }
        return cachedObject;
    }

    // TODO : disk cache 구현하기
    private static <T extends ProviderInterface> CacheObject findInDisk(Class<T> targetClass, String key){
        return null;
    }

    private static void registerInMemory(CacheObject cacheObject, String key){
        lruMemCache.put(cacheObject, key);
    }

    public static <T extends ProviderInterface> void put(T value, String key){
        checkInitAndThrow();

        CacheObject<T> cacheObject = new CacheObject<>(
                key,
                value,
                System.currentTimeMillis() + value.getCacheTime()
        );
        lruMemCache.put(key, cacheObject);
    }

    private static boolean isExpired(CacheObject cacheObject){
        long currentTime = System.currentTimeMillis();
        if(cacheObject.getExpireTime() < currentTime){
            return true;
        }
        return false;
    }

    private static void checkInitAndThrow(){
        if(isInitialized() == false){
            throw new RuntimeException("You need to start it through the Bank.Builder");
        }
    }

    public static int getMemCacheSize() {
        checkInitAndThrow();

        return memCacheSize;
    }

    public static int getDiskCacheSize() {
        checkInitAndThrow();

        return diskCacheSize;
    }

    public static CacheMode getCacheMode() {
        checkInitAndThrow();

        return cacheMode;
    }

    public static void clear(){
        checkInitAndThrow();

        clearDiskCache();
        clearMemoryCache();
    }

    private static void clearMemoryCache(){
        lruMemCache.evictAll();
    }

    private static void clearDiskCache(){
        try {
            if(lruDiskCache != null) {
                lruDiskCache.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException", e);
        }
    }

    public static void terminate(){
        checkInitAndThrow();

        clear();
        isInitialized = false;
        lruMemCache = null;
    }

    public final static class Builder {

        private int memCacheSize;
        private int diskCacheSize;
        private CacheMode cacheMode;

        public Builder(){
            memCacheSize = BankConstant.DEFAULT_MEM_CACHE_SIZE;
            diskCacheSize = BankConstant.DEFAULT_DISK_CACHE_SIZE;
            cacheMode = BankConstant.DEFAULT_CACHE_MODE;
        }

        public Bank init(){
            return new Bank(this);
        }

        public Builder setMemCacheSize(int memCacheSize) {
            this.memCacheSize = memCacheSize;
            return this;
        }

        public Builder setDiskCacheSize(int diskCacheSize) {
            this.diskCacheSize = diskCacheSize;
            return this;
        }

        public Builder setCacheMode(CacheMode cacheMode){
            this.cacheMode = cacheMode;
            return this;
        }
    }
}
