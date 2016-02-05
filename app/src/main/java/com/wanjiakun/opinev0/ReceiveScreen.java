package com.wanjiakun.opinev0;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wanjiakun.adapter.AppraisalAdapter;

import java.util.List;


public class ReceiveScreen extends ActionBarActivity {
    public final static String INDEX = "com.wanjiakun.opinev0.INDEX";
    private AppraisalAdapter adapter;
    private List<ParseObject> appraisals;
    boolean created = false;

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_screen);

        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.primaryDark));

        initializeList();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(created)overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);

        refreshList();
    }

    private void initializeList(){
        appraisals = User.getInstance("u").getAppraisals();

        adapter = new AppraisalAdapter(this, appraisals);

        list = (ListView) findViewById(R.id.list_appraisal);
        list.setAdapter(adapter);
    }

    private void refreshList(){
        User.getInstance("u").refreshAppraisals();
        initializeList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh_appraisal) {
            refreshList();
        }

        return super.onOptionsItemSelected(item);
    }

    public void pickAppraisal(View view){
        created = true;
        int i = Integer.parseInt(view.getTag().toString());
        appraisals.get(i).put("read", true);
        appraisals.get(i).saveInBackground();
        Intent intent = new Intent(this, AppraisalScreen.class);
        intent.putExtra(INDEX, i);
        startActivity(intent);
    }
}
