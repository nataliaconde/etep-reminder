package com.etep.reminder;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.livequery.ParseLiveQueryClient;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", getString(R.string.gcmSenderId));
        ParseUser user = ParseUser.getCurrentUser();
        if(user != null){
            installation.put("userId", user.getObjectId());
        }
        installation.saveInBackground();


    }


}
