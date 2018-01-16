package net.jspiner.cachebank;

import android.util.Log;

import java.util.NoSuchElementException;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public final class BaseCacheable<K, T> implements Cacheable<K, T> {

    private Class<T> targetClass;
    private K key;
    private T value;
    private CacheableMode cacheableMode;
    private DataSource<K, T> dataSource;

    protected BaseCacheable(Class<T> targetClass, K key, CacheableMode cacheableMode) {
        this.targetClass = targetClass;
        this.key = key;
        this.cacheableMode = cacheableMode;
    }

    protected BaseCacheable(T value, K key, CacheableMode cacheableMode) {
        this.value = value;
        this.key = key;
        this.cacheableMode = cacheableMode;
    }

    @Override
    public T now() {
        try {
            return getCacheObservable().blockingFirst();
        }
        catch (NoSuchElementException exception){
            // observable return nothing
            return null;
        }
    }

    @Override
    public Observable<T> rx() {
        return getCacheObservable();
    }

    @Override
    public Disposable subscribe(Consumer<T> consumer) {
        return getCacheObservable().subscribe(consumer);
    }

    @Override
    public Cacheable dataSource(DataSource<K, T> dataSource) {
        this.dataSource = dataSource;
        return this;
    }

    private Observable<T> getCacheObservable() {
        switch (cacheableMode) {
            case DEPOSIT:
                return deposit();
            case WITHDRAW:
                return withdraw();
            default:
                throw new RuntimeException("undefined enum case : " + cacheableMode);
        }
    }

    private Observable<T> withdraw() {
        return Observable.create((ObservableOnSubscribe<CacheObject>) emmiter -> {
            CacheObject<K, T> cachedObject = getCacheObject(targetClass, key);

            if (cachedObject == null) {
                throw new NullPointerException("cannot update new data");
            }
            emmiter.onNext(cachedObject);
            emmiter.onComplete();

        }).flatMap(cacheObject -> cacheObject.getValueObservable());
    }

    private CacheObject getCacheObject(Class<T> targetClass, K key) {
        CacheObject<K, T> cachedObject = getCacheObjectInCache(targetClass, key);

        boolean isExpired = isExpired(cachedObject);

        if (isExpired && dataSource != null) {
            cachedObject.update(key, dataSource);
        }

        return cachedObject;
    }

    private <T, K> CacheObject getCacheObjectInCache(Class<T> targetClass, K key) {
        CacheObject cachedObject = findInMemory(targetClass, key);

        if (cachedObject == null) {
            cachedObject = findInDisk(targetClass, key);

            if (cachedObject != null) {
                registerInMemory(cachedObject, key);
            }
        }

        if (cachedObject == null) {
            cachedObject = CacheObject.newInstance(targetClass, key);
        }

        return cachedObject;
    }

    private <T, K> CacheObject findInMemory(Class<T> targetClass, K key) {
        CacheObject<K, T> cachedObject = (CacheObject<K, T>) Bank.getMemCache().get(key);
        if (cachedObject == null) {
            return null;
        }
        if (!cachedObject.getValue().getClass().equals(targetClass)) {
            throw new ClassCastException();
        }
        return cachedObject;
    }

    // TODO : disk cache 구현하기
    private static <T, K> CacheObject findInDisk(Class<T> targetClass, K key) {
        return null;
    }

    private static <T, K> void registerInMemory(CacheObject cacheObject, K key) {
        Bank.getMemCache().put(cacheObject, key);
    }

    private Observable deposit() {
        // TODO : default 시간이 아닌 캐시모듈별 시간으로 처리하도록 구현 필요
        return Observable.create(
                emitter -> {
                    CacheObject<K, T> cacheObject = new CacheObject<>(
                            key,
                            value,
                            System.currentTimeMillis() + 1000
                    );
                    Bank.getMemCache().put(key, cacheObject);

                    emitter.onNext(value);
                    emitter.onComplete();
                }
        );
    }

    private static boolean isExpired(CacheObject cacheObject) {
        long currentTime = System.currentTimeMillis();
        if (cacheObject.getExpireTime() < currentTime) {
            return true;
        }
        return false;
    }

}
