CacheBank 
![travis](https://travis-ci.org/JSpiner/CacheBank.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/JSpiner/CacheBank/badge.svg?branch=master)](https://coveralls.io/github/JSpiner/CacheBank?branch=master)
[![codebeat badge](https://codebeat.co/badges/a03172bd-ff9d-4d3b-88ed-29f48a2d8b01)](https://codebeat.co/projects/github-com-jspiner-cachebank-master)

---------------
CacheBank is rx based android memory-disk cache library

(will) supporting mem/disk cache

# STILL DEVELOPING!!
will support...
- RxJava
- Disk Cache
- Cache Option
ASAP....

Cache flow
-----------------------
ASAP....

Structure
-------------------
ASAP....

Set up
-------------
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


Define DataModel
----------------
```java
class CarModel extends Provider<CarModel> {

    public int index;
    public String carName;

    //optional, if you don't override, use default
    @Override
    public int getCacheTime() {
        return 1 * 1000;
    }

    //you can choose in below
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

Get Data(sync)
----------------------
```java

void setItemLayout(String carId){
    CarModel carModel = Bank.getNow(carId, CarModel.class);

    textView.setText(carModel.carName);
    ...
}

```

Get Data Using Observable(async)
----------------------
```java

void setItemLayout(String carId){
    Observable<CarModel> carObservable = Bank.get(carId, CarModel.class);
    carObservable.subscribe(carModel -> {
            textView.setText(carModel.carName);
        }
    );
    ...
}

```


Put Data
------------------------
```java
void networkResponseCallback(String carId, CarModel carModel){
    Bank.put(carId, carModel);
}

```
