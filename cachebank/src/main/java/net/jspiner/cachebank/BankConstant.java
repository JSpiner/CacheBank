package net.jspiner.cachebank;

/**
 * Created by JSpiner on 2017. 7. 13..
 * JSpiner
 * Contact : jspiner@naver.com
 */

public final class BankConstant {

    public static int DEFAULT_MEM_CACHE_SIZE = 4 * 1024 * 1024; // 4mb
    public static int DEFAULT_DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10mb
    public static long DEFAULT_CACHE_TIME = 1000; // 1 second
    public static CacheMode DEFAULT_CACHE_MODE = CacheMode.ALL;

}
