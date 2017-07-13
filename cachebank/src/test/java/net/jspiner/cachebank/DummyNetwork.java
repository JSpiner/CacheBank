package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public class DummyNetwork {

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

    public static CarModel request(String key){
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

    public static CarModel request(){
        return new CarModel(
            1255,
            "avante"
        );
    }

    public static CarModel request(CarModel carModel){
        if(carModel == null){
            return request();
        }
        return new CarModel(
                carModel.index + 1,
                "sonata"
        );
    }
}
