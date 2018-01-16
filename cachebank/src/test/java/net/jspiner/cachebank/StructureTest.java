package net.jspiner.cachebank;

import net.jspiner.cachebank.model.CarModel;

/**
 * Created by prnd on 2018. 1. 16..
 */

public class StructureTest {

    public void test(){
        new Bank.Builder()
                .setCacheMode(CacheMode.ALL)
                .init();

        /*
        Bank.deposit(
                CarModel.class,
                "1911"
        ).dataSource(dataSource)
         .rx()
         .subscribe(carModel -> hi);*/

    }

}
