CacheBank
---------------
CacheBank is android mem-disk cache library

(will) supporting mem/disk cache

####
STILL DEVELOPING!!

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
class CarModel implements ProviderInterface<CarModel> {

    public int index;
    public String carName;

    @Override
    public CarModel initData() {
        return yourFetchDataFunction();
    }

    @Override
    public CarModel updateData(CarModel prevData) {
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