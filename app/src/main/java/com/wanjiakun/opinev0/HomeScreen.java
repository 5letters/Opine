package com.wanjiakun.opinev0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

public class HomeScreen extends Activity {
    private int path; //1 for give, 2 for receive

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        path = 0;

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.primaryDark));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(path == 1){
            overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
        }else if(path == 2){
            overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
        }
    }

    public void facebookLogin(View view){
        Button login = (Button) findViewById(R.id.button_login_facebook);
        login.setText("Logging In...");
        ParseFacebookUtils.logIn(Arrays.asList("email", "user_friends", "public_profile"), this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                }else if (user.isNew()) {
                    registerUser();
                    proceed();
                }else{
                    proceed();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    private void proceed(){
        User.getInstance(ParseUser.getCurrentUser().getUsername());
        setContentView(R.layout.activity_splash_screen);
    }

    private void registerUser(){
        Request.newMeRequest(Session.getActiveSession(), new Request.GraphUserCallback(){
            @Override
            public void onCompleted(GraphUser user, Response response) {
                if (user != null) {
                    String id = user.getId();
                    Log.d("NAME", user.getName());
                    Log.d("ID", id);
                    ParseObject userProfile = new ParseObject("UserProfile");
                    userProfile.put("username", ParseUser.getCurrentUser().getUsername());
                    userProfile.put("IDcode", id);
                    userProfile.put("name", user.getName());
                    userProfile.saveInBackground();
                }
            }
        }).executeAsync();
    }

    public void Give(View view){
        path = 1;
        Intent intent = new Intent(this, GiveScreen.class);
        startActivity(intent);
    }

    public void Receive(View view){
        path = 2;
        Intent intent = new Intent(this, ReceiveScreen.class);
        startActivity(intent);
    }

}
