package net.jspiner.cachebank;

import android.support.v4.util.LruCache;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public final class Bank {

    private static int memCacheSize;
    private static int diskCacheSize;
    private static boolean isInitialized = false;
    private static LruCache lruMemCache;
    private static CacheMode cacheMode;

    private Bank(Builder builder){
        this.memCacheSize = builder.memCacheSize;
        this.diskCacheSize = builder.diskCacheSize;
        this.cacheMode = builder.cacheMode;
        this.isInitialized = true;
        this.lruMemCache = new LruCache<String, CacheObject>(memCacheSize);
    }

    public static boolean isInitialized(){
        return isInitialized;
    }

    public static <T extends ProviderInterface> T get(String key, Class<T> targetClass){
        CacheObject<T> cachedObject = getCacheObjectInCache(key, targetClass);

        boolean isExpired = isExpired(cachedObject);

        if(isExpired){
            cachedObject.update(key);
        }

        return cachedObject.getValue();
    }

    private static <T extends ProviderInterface> CacheObject getCacheObjectInCache(String key, Class<T> targetClass){
        CacheObject cachedObject = findInMemory(key, targetClass);

        if(cachedObject == null){
            cachedObject = findInDisk(key, targetClass);

            if(cachedObject != null){
                registerInMemory(key, cachedObject);
            }
        }

        if(cachedObject == null){
            cachedObject = CacheObject.newInstance(key, targetClass);
        }

        return cachedObject;
    }

    private static <T extends ProviderInterface> CacheObject findInMemory(String key, Class<T> targetClass){
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
    private static <T extends ProviderInterface> CacheObject findInDisk(String key, Class<T> targetClass){
        return null;
    }

    private static void registerInMemory(String key, CacheObject cacheObject){
        lruMemCache.put(key, cacheObject);
    }

    public static <T extends ProviderInterface> void put(String key, T value){
        CacheObject<T> cacheObject = new CacheObject<>(
                key,
                value,
                System.currentTimeMillis() + value.getCacheTime()
        );
        lruMemCache.put(key, cacheObject);
    }

    public static boolean isExpired(CacheObject cacheObject){
        long currentTime = System.currentTimeMillis();
        if(cacheObject.getExpireTime() < currentTime){
            return true;
        }
        return false;
    }

    public static int getMemCacheSize() {
        return memCacheSize;
    }

    public static int getDiskCacheSize() {
        return diskCacheSize;
    }

    public static CacheMode getCacheMode() {
        return cacheMode;
    }

    public static void clear(){
        clearDiskCache();
        clearMemoryCache();
    }

    // TODO : 메모리와 디스크에서 캐시 초기화하는 함수 구현하기
    private static void clearMemoryCache(){

    }

    private static void clearDiskCache(){

    }

    public static void terminate(){
        isInitialized = false;
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
