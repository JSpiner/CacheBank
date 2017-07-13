package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@gmail.com
 */

public interface ProviderInterface<T> {

    int getCacheTime();
    T fetchData(String key, T prevData);

}
