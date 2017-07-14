package net.jspiner.cachebank;

import android.support.annotation.Nullable;

import io.reactivex.Observable;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@prnd.co.kr
 */

public abstract class Provider<T> implements ProviderInterface<T> {

    public Provider(){
        super();
    }

    @Override
    public int getCacheTime() {
        return BankConstant.DEFAULT_CACHE_TIME;
    }

    @Override
    public T fetchData(String key, @Nullable T prevData) {
        return null;
    }

    @Override
    public Observable<T> fetchDataObservable(String key, @Nullable T prevData) {
        return null;
    }
}
