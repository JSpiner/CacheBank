package net.jspiner.cachebank.model;

import android.support.annotation.Nullable;

import io.reactivex.Observable;

/**
 * Created by JSpiner on 2017. 7. 14..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public class AnimalModel {

    public int index;
    public String animalName;

    public AnimalModel(int index, String animalName){
        this.index = index;
        this.animalName = animalName;
    }
}
