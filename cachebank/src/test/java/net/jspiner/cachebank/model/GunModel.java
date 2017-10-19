package net.jspiner.cachebank.model;

import android.support.annotation.Nullable;

import net.jspiner.cachebank.Provider;

import io.reactivex.Observable;

public class GunModel implements Provider<GunModel> {

    private GunModel(){

    }

    @Override
    public int getCacheTime() {
        return 0;
    }

    @Override
    public GunModel fetchData(String key, @Nullable GunModel prevData) {
        return null;
    }

    @Override
    public Observable<GunModel> fetchDataObservable(String key, @Nullable GunModel prevData) {
        return null;
    }
}
