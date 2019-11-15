package com.example.infs3604homework3;

import android.app.Application;
import android.content.Context;


// This class allows for the obtaining of the application's Context at any given point in the application.
public class AppContext extends Application {

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }

}
