package com.example.instaclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("gsaPmsKYKqG5mK0Kd2XYdZrXjU9Jw5hsfmAQdzwG")
                // if defined
                .clientKey("rEjHKzYQrrZtCR6n5mOtFOkW5Zrk80ztnFOW2EFW")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
