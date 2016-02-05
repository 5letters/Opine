package com.wanjiakun.opinev0;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.ParseUser;
import com.wanjiakun.adapter.FriendsAdapter;

import java.util.List;


public class GiveScreen extends ActionBarActivity {
    public final static String USER_NAME = "com.wanjiakun.opinev0.NAME";
    public final static String USER_ID = "com.wanjiakun.opinev0.ID";

    FriendsAdapter adapter;
    String[][] friends;
    List<Bitmap> photos;

    boolean created = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_screen);

        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.primaryDark));

        initalizeList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(created) overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    private void initalizeList(){
        friends = User.getInstance("u").getFriends();
        photos = User.getInstance("u").getFriendPhotos();

        adapter = new FriendsAdapter(this, Session.getActiveSession(), friends, photos);

        ListView list = (ListView) findViewById(R.id.list_friends);
        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_give_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pickFriend(View view){
        created = true;
        int i = Integer.parseInt(view.getTag().toString());
        Intent intent = new Intent(this, SubmitScreen.class);
        intent.putExtra(USER_NAME, friends[i][0]);
        intent.putExtra(USER_ID, friends[i][1]);
        startActivity(intent);
    }
}
