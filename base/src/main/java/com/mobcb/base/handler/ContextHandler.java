package com.mobcb.base.handler;

import android.content.Context;

/**
 * Created by Studio on 2018/3/26.
 */

public class ContextHandler {
    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        ContextHandler.appContext = appContext;
    }
}
