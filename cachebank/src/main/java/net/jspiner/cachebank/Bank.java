package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public final class Bank {

    private static int memCacheSize;
    private static int diskCacheSize;
    private static boolean isInitialized = false;

    private Bank(Builder builder){
        this.memCacheSize = builder.memCacheSize;
        this.diskCacheSize = builder.diskCacheSize;
        this.isInitialized = true;
    }

    public static boolean isInitialized(){
        return isInitialized;
    }

    public static <T extends ProviderInterface> T get(String key, Class<T> targetClass){
        CacheObject<T> cacheObject = CacheObject.find(key, targetClass);
        if(cacheObject == null){
            throw new NullPointerException();
        }
        boolean isExpired = isExpired(cacheObject);

        if(isExpired){
            cacheObject.update();
        }

        return cacheObject.getValue();
    }

    public static boolean isExpired(CacheObject cacheObject){
        long currentTime = System.currentTimeMillis();
        if(cacheObject.getExpireTime() > currentTime){
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

    public static void clear(){

    }

    public static void terminate(){
        isInitialized = false;
    }

    public final static class Builder {

        private int memCacheSize;
        private int diskCacheSize;

        public Builder(){
            memCacheSize = BankConstant.DEFAULT_MEM_CACHE_SIZE;
            diskCacheSize = BankConstant.DEFAULT_DISK_CACHE_SIZE;
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
    }
}
