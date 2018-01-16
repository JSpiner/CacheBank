package net.jspiner.cachebank;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface Cacheable<K, T> {

    T now();

    Observable<T> rx();

    Disposable subscribe(Consumer<T> consumer);

    Cacheable dataSource(DataSource<K, T> dataSource);

}
