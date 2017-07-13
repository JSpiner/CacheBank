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

    public static <T extends ProviderInterface> CacheObject find(String key, Class<T> targetClass){
        CacheObject cacheObject = new CacheObject<T>(
                key,
                getTargetInstance(targetClass),
                0
        );
        return cacheObject;
    }

    private static <T> T getTargetInstance(Class<T> targetClass){
        try {
            return targetClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(){
        value = (T)value.updateData(value);
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
