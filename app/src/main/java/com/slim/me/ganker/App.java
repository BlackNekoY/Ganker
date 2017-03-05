package com.slim.me.ganker;

import android.app.Application;
import android.content.Context;


/**
 * Created by Slim on 2017/2/19.
 */

public class App extends Application {

    // only use on DataManager's onInit
    // don't use it in other way!
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

}
