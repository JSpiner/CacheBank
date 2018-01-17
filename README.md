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

## Usage

### Set up
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
class CarModel implements Provider<CarModel> {

    public int index;
    public String carName;

    @Override
    public int getCacheTime() {
        return 1 * 1000;
    }

    @Override
    public CarModel fetchData(String key, CarModel prevData) {
        return yourFetchDataFunction(key);
    }

    @Override
    public Observable<CarModel> fetchDataObservable(String key, @Nullable CarModel prevData) {
        return super.fetchDataObservable(key, prevData);
    }

}

```

### Get Data(sync)
```java

void setItemLayout(String carId){
    CarModel carModel = Bank.withdraw(carId, CarModel.class).now();

    textView.setText(carModel.carName);
    ...
}

```

Get Data Using Observable(async)
----------------------
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


### Put Data(sync)
```java
void networkResponseCallback(String carId, CarModel carModel){
    Bank.deposit(carId, carModel).now();
}

```



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
