package com.wanjiakun.opinev0;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class SubmitScreen extends ActionBarActivity {
    private String NAME;
    private String ID;
    private String fromID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_screen);

        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.primaryDark));

        Intent intent = getIntent();
        NAME = intent.getStringExtra(GiveScreen.USER_NAME);
        ID = intent.getStringExtra(GiveScreen.USER_ID);

        fromID = User.getInstance("u").getFacebookID();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_upload_confirm) {
            EditText subject = (EditText) findViewById(R.id.edit_subject);
            EditText message = (EditText) findViewById(R.id.edit_message);
            ParseObject appraisal = new ParseObject("Appraisal");
            appraisal.put("to", ID);
            appraisal.put("from", fromID);
            appraisal.put("subject", subject.getText().toString());
            appraisal.put("message", message.getText().toString());

            appraisal.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    finish();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
