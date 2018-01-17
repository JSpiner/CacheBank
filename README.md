CacheBank 
---------------

![travis](https://travis-ci.org/JSpiner/CacheBank.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/JSpiner/CacheBank/badge.svg?branch=master)](https://coveralls.io/github/JSpiner/CacheBank?branch=master)
[![codebeat badge](https://codebeat.co/badges/a03172bd-ff9d-4d3b-88ed-29f48a2d8b01)](https://codebeat.co/projects/github-com-jspiner-cachebank-master)

CacheBank is rx based android memory-disk cache library

supporting mem/disk cache

# STILL DEVELOPING!!
now support 
- RxJava
- Mem LRUCache

will support...
- Disk Cache(Dual mode)
- Cache Option

## Basic Usage

### Set up
You must initialize the `Bank` before using it.

```java
new Bank.Builder().init();
```

or you canspecify values...
```java
new Bank.Builder()
    .setMemCacheSize(100)
    .setDiskCacheSize(100)
    .init();
```


### Define DataModel
```java
class CarModel {

    public int index;
    public String carName;

}

```

### Put Data(sync)
You can store the data using the `deposit()` function.

```java
void networkResponseCallback(String carId, CarModel carModel){
    Bank.deposit(carId, carModel).now();
}

```

The `deposit()` function returns `Cacheable` object.

`Cacheable` object has 3 functions. `now`, `rx`, `subscribe`.

If you want to call a function synchronously, you can use `now`.

Or if you want to call a function asynchronously, you can use `rx` or `subscribe` like below.


### Put Data(async)
```java
void networkResponseCallback(String carId, CarModel carModel){
    Observable putObservable = Bank.deposit(carId, carModel).rx();
    putObservable.subscribe(__ -> Logger.i("saved!"));
}

//or more simply
void networkResponseCallbackSimply(String carId, CarModel carModel){
    Bank.deposit(carId, carModel).subscribe(__ -> Logger.i("saved!"));
}

```


### Get Data(sync)
You can load the data as your setting it at initialization. (cachetime, cachemode... etc)

```java

void setItemLayout(String carId){
    CarModel carModel = Bank.withdraw(carId, CarModel.class).now();

    textView.setText(carModel.carName);
    ...
}

```

Like the 'deposit' function, the 'withdrawal' function also returns Cacheable.
Equally, it has three functions. `now`, `rx`, `subscribe`
Async functions return the cached data or nothing.

### Get Data Using Observable(async)
```java

void setItemLayout(String carId){
    Observable<CarModel> carObservable = Bank.withdraw(carId, CarModel.class).rx();
    carObservable.subscribe(carModel -> {
            textView.setText(carModel.carName);
        }
    );
    ...
}

//or more simply
void setItemLayoutSimply(String carId){
    Bank.withdraw(carId, CarModel.class)
        .subscribe(carModel -> {
                textView.setText(carModel.carName);
            }
        );
}

```


## Advanced Usage

### CacheMode
You can specify cache mode in initialize.

```java
    new Bank.Builder()
            .setMemCacheSize(100)
            .setDiskCacheSize(100)
            .setCacheMode(CacheMode.MEMORY_ONLY)
            .init();
```

There are 3 options. `MEMORY_ONLY`, `DISK_ONLY`, `ALL`

### DataSource
For a more clear code pattern, it support `DataSource`.

If `DataSource` is defined, data is automatically updated from the `DataSource` only when there is no cache data.

Like this.

```java
// DataSource<KeyType, DataType>
public class CarDataSource implements DataSource<String, CarModel> {

    @Override
    public void fetchData(String key, DataEmitter<CarModel> emitter) {
        DummyNetwork.requestCar(key).subscribe(
            carModel -> {
                emitter.emit(carModel);
            }
        );
    }

}
```

And set datasource when you use `withdrawal` function.

```java
CarModel carModel = Bank.withdrawal(CarModell.class, "sonata")
                        .dataSource(new CarDataSource()).now();
textView.setText(carModel.carName);
```

Request new data from datasource only if there is no cached data.

Of course, you can use it with rxJava.(I recommend it)

```java
CarModel carModel = Bank.withdrawal(CarModell.class, "sonata")
                        .dataSource(new CarDataSource())
                        .rx()
                        .subscribe(
                            carModel -> textView.setText(carModel.carName)
                        );
```
