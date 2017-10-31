package net.jspiner.cachebank;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public final class BaseCacheable<T extends Provider> implements Cacheable<T> {

    private Class<T> targetClass;
    private String key;
    private T value;
    private CacheableMode cacheableMode;

    protected BaseCacheable(Class<T> targetClass, String key, CacheableMode cacheableMode){
        this.targetClass = targetClass;
        this.key = key;
        this.cacheableMode = cacheableMode;
    }

    protected BaseCacheable(T value, String key, CacheableMode cacheableMode){
        this.value = value;
        this.key = key;
        this.cacheableMode = cacheableMode;
    }

    @Override
    public T now() {
        return getCacheObservable().blockingFirst();
    }

    @Override
    public Observable<T> rx() {
        return getCacheObservable();
    }

    @Override
    public Disposable subscribe(Consumer<T> consumer) {
        return getCacheObservable().subscribe(consumer);
    }

    private Observable<T> getCacheObservable(){
        switch (cacheableMode){
            case DEPOSIT:
                return deposit();
            case WITHDRAW:
                return withdraw();
            default:
                throw new RuntimeException("undefined enum case : " + cacheableMode);
        }
    }

    private Observable<T> deposit(){
        return Observable.create((ObservableOnSubscribe<CacheObject>) emmiter -> {
            CacheObject<T> cachedObject = getCacheObject(targetClass, key);

            if(cachedObject == null){
                throw new NullPointerException("cannot update new data");
            }
            emmiter.onNext(cachedObject);
            emmiter.onComplete();

        }).flatMap(cacheObject -> cacheObject.getValueObservable());
    }

    private <T extends Provider> CacheObject getCacheObject(Class<T> targetClass, String key){
        CacheObject<T> cachedObject = getCacheObjectInCache(targetClass, key);

        boolean isExpired = isExpired(cachedObject);

        if(isExpired){
            cachedObject.update(key);
        }

        return cachedObject;
    }

    private <T extends Provider> CacheObject getCacheObjectInCache(Class<T> targetClass, String key){
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

    private <T extends Provider> CacheObject findInMemory(Class<T> targetClass, String key){
        CacheObject<T> cachedObject = (CacheObject<T>) Bank.getMemCache().get(key);
        if(cachedObject == null){
            return null;
        }
        if(!cachedObject.getValue().getClass().equals(targetClass)){
            throw new ClassCastException();
        }
        return cachedObject;
    }

    // TODO : disk cache 구현하기
    private static <T extends Provider> CacheObject findInDisk(Class<T> targetClass, String key){
        return null;
    }

    private static void registerInMemory(CacheObject cacheObject, String key){
        Bank.getMemCache().put(cacheObject, key);
    }

    private Observable withdraw(){
        return Observable.create(
                emitter -> {
                    CacheObject<T> cacheObject = new CacheObject<>(
                            key,
                            value,
                            System.currentTimeMillis() + value.getCacheTime()
                    );
                    Bank.getMemCache().put(key, cacheObject);

                    emitter.onNext(value);
                    emitter.onComplete();
                }
        );
    }

    private static boolean isExpired(CacheObject cacheObject){
        long currentTime = System.currentTimeMillis();
        if(cacheObject.getExpireTime() < currentTime){
            return true;
        }
        return false;
    }

}
