package net.jspiner.cachebank;

import net.jspiner.cachebank.model.CarModel;
import net.jspiner.cachebank.model.FoodModel;

import io.reactivex.Observable;

/**
 * Created by JSpiner on 2017. 7. 13..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public class DummyNetwork {

    /*
    Dummy CarModel
     */
    private static CarModel sonataModel = new CarModel(
            1255,
            "sonata"
    );
    private static CarModel avanteModel = new CarModel(
            1256,
            "avante"
    );
    private static CarModel i30Model = new CarModel(
            1257,
            "i30"
    );

    /*
    Dummy FoodModel
     */
    private static FoodModel burgerModel = new FoodModel(
            2311,
            "burger"
    );
    private static FoodModel breadModel = new FoodModel(
            2312,
            "bread"
    );
    private static FoodModel pizzaModel = new FoodModel(
            2313,
            "pizza"
    );


    public static CarModel requestCar(String key){
        if(key == null){
            return null;
        }
        if(key.equals("sonata"))
            return sonataModel;
        if(key.equals("avante"))
            return avanteModel;
        if(key.equals("i30"))
            return i30Model;
        return null;
    }

    public static FoodModel requestFood(String key){
        if(key == null){
            return null;
        }
        if(key.equals("burger"))
            return burgerModel;
        if(key.equals("bread"))
            return breadModel;
        if(key.equals("pizza"))
            return pizzaModel;
        return null;
    }

    public static Observable<FoodModel> requestFoodObservable(String key){
        return Observable.create(emitter -> {
            emitter.onNext(requestFood(key));
            emitter.onComplete();
        });
    }


}
