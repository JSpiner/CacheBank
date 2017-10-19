package net.jspiner.cachebank.model;

import android.support.annotation.Nullable;

import net.jspiner.cachebank.Provider;

import io.reactivex.Observable;

/**
 * Created by JSpiner on 2017. 7. 14..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public class AnimalModel implements Provider<AnimalModel> {

    public int index;
    public String animalName;

    public AnimalModel(int index, String animalName){
        this.index = index;
        this.animalName = animalName;
    }

    @Override
    public int getCacheTime() {
        return 500;
    }

    @Override
    public AnimalModel fetchData(String key, @Nullable AnimalModel prevData) {
        return null;
    }

    @Override
    public Observable<AnimalModel> fetchDataObservable(String key, @Nullable AnimalModel prevData) {
        return null;
    }
}
