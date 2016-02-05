package com.wanjiakun.opinev0;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wanjiakun on 6/9/2015.
 */
public class User {
    private static User me = null;

    private String user;

    private ParseObject profile;

    private String[][] friends;
    private List<Bitmap> friendPhotos;
    private String facebookID;

    private List<ParseObject> appraisals;

    private User(String u){
        user = u;
        friendPhotos = new ArrayList<Bitmap>();
        initFriends();
        initAppraisals();
    }

    public static User getInstance(String u){
        if(me == null){
            me = new User(u);
        }
        return me;
    }

    public void initFriends() {
        Request.newMyFriendsRequest(Session.getActiveSession(), new Request.GraphUserListCallback() {
            @Override
            public void onCompleted(List<GraphUser> users, Response response) {
                if (users != null) {
                    friends = new String[users.size()][2];
                    for (int i = 0; i < users.size(); i++) {
                        friends[i][0] = users.get(i).getName();
                        Log.e("Friend", friends[i][0]);
                        friends[i][1] = users.get(i).getId();
                        Log.e("Friend", friends[i][1]);
                    }
                    initPhotos();
                }
            }
        }).executeAsync();
    }

    public void initAppraisals(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserProfile");
        query.whereEqualTo("username", user);
        try {
            profile = query.getFirst();
            facebookID = profile.getString("IDcode");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        query = ParseQuery.getQuery("Appraisal");
        query.whereEqualTo("to", facebookID);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    Log.d("Appraisal", "found " + parseObjects.size());
                    appraisals = parseObjects;
                } else {
                    Log.d("Appraisal", "found nothing");
                }

            }
        });
    }

    public void initPhotos(){
        String[] ids = new String[friends.length];
        for(int i = 0; i < friends.length;i++){
            ids[i] = friends[i][1];
        }
        new DownloadPhotosTask().execute(ids);
    }

    public void refreshAppraisals(){
        initAppraisals();
    }

    public void refreshFriends(){
        initFriends();
    }

    public String getUser() {
        return user;
    }

    public ParseObject getProfile() {
        return profile;
    }

    public String[][] getFriends() {
        return friends;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public List<ParseObject> getAppraisals() {
        return appraisals;
    }

    public List<Bitmap> getFriendPhotos() {
        return friendPhotos;
    }

    private class DownloadPhotosTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            for(int i = 0; i < params.length; i++){
                try {
                    URL imgUrl = new URL("https://graph.facebook.com/" + params[i] + "/picture?type=large");
                    InputStream in = (InputStream) imgUrl.getContent();
                    friendPhotos.add(BitmapFactory.decodeStream(in));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            Log.d("ASYNC", "DONE!");
            return null;
        }

    }
}
