package com.wanjiakun.opinev0;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by Wanjiakun on 3/13/2015.
 */
public class OpineV0 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "iC8izkG1a1ZLKUVHfZFSYLslFrH8TFTZovs07Tyr", "R2vIfkrEyMwI9q1vH2F59FjkJ6G7Uzp6bXv2Byb5");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
