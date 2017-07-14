CacheBank 
![travis](https://travis-ci.org/JSpiner/CacheBank.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/JSpiner/CacheBank/badge.svg?branch=master)](https://coveralls.io/github/JSpiner/CacheBank?branch=master)
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

    @Override
    public CarModel fetchData(String key, CarModel prevData) {
        return yourFetchDataFunction(key);
    }

}

```

Get Data
----------------------
```java

void setItemLayout(String carId){
    CarModel carModel = Bank.get(carId, CarModel.class);

    textView.setText(carModel.carName);
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