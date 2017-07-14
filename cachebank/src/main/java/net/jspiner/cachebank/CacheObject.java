package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

class CacheObject<T extends ProviderInterface> {

    private long expireTime;
    private String key;
    private T value;

    public CacheObject(String key, T value, long expireTime){
        this.key = key;
        this.value = value;
        this.expireTime = expireTime;
    }

    public static <T extends ProviderInterface> CacheObject bind(String key, Class<T> targetClass){
        CacheObject cacheObject = new CacheObject<T>(
                key,
                getTargetInstance(targetClass),
                0
        );
        return cacheObject;
    }

    public static <T extends ProviderInterface> CacheObject newInstance(String key, Class<T> targetClass){
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

    public void update(String key){
        value = (T) value.fetchData(key, value);
    }

    public long getExpireTime(){
        return expireTime;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
