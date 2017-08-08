package net.jspiner.cachebank.model;

import android.support.annotation.Nullable;

import net.jspiner.cachebank.Provider;

public class GunModel extends Provider<GunModel> {

    private GunModel(){

    }

    @Override
    public GunModel fetchData(String key, @Nullable GunModel prevData) {
        return null;
    }
}
