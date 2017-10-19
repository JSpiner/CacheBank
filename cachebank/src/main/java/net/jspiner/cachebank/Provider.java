package net.jspiner.cachebank;

import android.support.annotation.Nullable;

import io.reactivex.Observable;

/**
 * Created by JSpiner on 2017. 7. 13..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public interface Provider<T> {

    int getCacheTime();
    T fetchData(String key, @Nullable T prevData);
    Observable<T> fetchDataObservable(String key, @Nullable T prevData);

}
