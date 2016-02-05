package com.wanjiakun.opinev0;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.parse.ParseObject;


public class AppraisalScreen extends ActionBarActivity {
    private int INDEX;
    private ParseObject appraisal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraisal_screen);

        Intent intent = getIntent();
        INDEX = intent.getIntExtra(ReceiveScreen.INDEX, 0);

        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.primaryDark));

        setComments();

        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    private void setComments(){
        TextView subject = (TextView) findViewById(R.id.text_subject);
        TextView message = (TextView) findViewById(R.id.text_message);

        appraisal = User.getInstance("u").getAppraisals().get(INDEX);

        subject.setText(appraisal.getString("subject"));
        message.setText(appraisal.getString("message"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appraisal_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_appraisal_discard) {
            appraisal.deleteInBackground();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
