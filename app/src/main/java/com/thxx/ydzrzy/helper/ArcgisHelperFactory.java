package com.thxx.ydzrzy.helper;

public class ArcgisHelperFactory {

    public static synchronized ArcgisHelper createArcgisHelper() {
        ArcgisHelper arcgisHelper;
        synchronized (ArcgisHelperFactory.class) {
            arcgisHelper = new ArcgisHelper();
        }
        return arcgisHelper;
    }
}
