package net.jspiner.cachebank;

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

}
