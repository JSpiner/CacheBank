CacheBank 
![travis](https://travis-ci.org/JSpiner/CacheBank.svg?branch=master)
---------------
CacheBank is android mem-disk cache library

(will) supporting mem/disk cache


# STILL DEVELOPING!!
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
    public CarModel fetchData(CarModel prevData) {
        return yourFetchDataFunction(prevData);
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