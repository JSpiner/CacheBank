package net.jspiner.cachebank;

import android.support.annotation.Nullable;

/**
 * Created by JSpiner on 2017. 7. 13..
 * PRNDCompany
 * Contact : smith@prnd.co.kr
 */

public interface ProviderInterface<T> {

    int getCacheTime();
    T fetchData(String key, @Nullable T prevData);

}
