package net.jspiner.cachebank;

import android.support.v4.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
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
    private static long defaultCacheTimeInMillis;

    private Bank(Builder builder) {
        this.memCacheSize = builder.memCacheSize;
        this.diskCacheSize = builder.diskCacheSize;
        this.cacheMode = builder.cacheMode;
        this.isInitialized = true;
        this.lruMemCache = new LruCache<String, CacheObject>(memCacheSize);
        //this.lruDiskCache = DiskLruCache.open(null, 1, 2, Integer.MAX_VALUE);
        this.defaultCacheTimeInMillis = builder.defaultCacheTimeMillis;
    }

    public static boolean isInitialized() {
        return isInitialized;
    }

    public static <K, T> Cacheable<K, T> withdrawal(Class<T> targetClass, K key) {
        checkInitAndThrow();

        return new BaseCacheable(
                targetClass,
                key,
                CacheableMode.WITHDRAW
        );
    }

    public static <K, T> Cacheable<K, T> deposit(T value, K key) {
        checkInitAndThrow();

        return new BaseCacheable<K, T>(
                value,
                key,
                CacheableMode.DEPOSIT
        );
    }

    private static boolean isExpired(CacheObject cacheObject) {
        long currentTime = System.currentTimeMillis();
        if (cacheObject.getExpireTime() < currentTime) {
            return true;
        }
        return false;
    }

    private static void checkInitAndThrow() {
        if (isInitialized() == false) {
            throw new RuntimeException("You need to start it through the Bank.Builder");
        }
    }

    protected static LruCache getMemCache() {
        checkInitAndThrow();

        return lruMemCache;
    }

    protected static DiskLruCache getDiskCache() {
        checkInitAndThrow();

        return lruDiskCache;
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

    public static void clear() {
        checkInitAndThrow();

        clearDiskCache();
        clearMemoryCache();
    }

    private static void clearMemoryCache() {
        lruMemCache.evictAll();
    }

    private static void clearDiskCache() {
        try {
            if (lruDiskCache != null) {
                lruDiskCache.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException", e);
        }
    }

    public static void terminate() {
        checkInitAndThrow();

        clear();
        isInitialized = false;
        lruMemCache = null;
    }

    public final static class Builder {

        private int memCacheSize;
        private int diskCacheSize;
        private CacheMode cacheMode;
        private long defaultCacheTimeMillis;

        public Builder() {
            memCacheSize = BankConstant.DEFAULT_MEM_CACHE_SIZE;
            diskCacheSize = BankConstant.DEFAULT_DISK_CACHE_SIZE;
            cacheMode = BankConstant.DEFAULT_CACHE_MODE;
            defaultCacheTimeMillis = BankConstant.DEFAULT_CACHE_TIME;
        }

        public Bank init() {
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

        public Builder setCacheMode(CacheMode cacheMode) {
            this.cacheMode = cacheMode;
            return this;
        }

        public Builder setDefaultCacheTimeMillis(long defaultCacheTimeMillis) {
            this.defaultCacheTimeMillis = defaultCacheTimeMillis;
            return this;
        }
    }
}
