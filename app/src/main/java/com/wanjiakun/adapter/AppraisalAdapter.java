package com.wanjiakun.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.wanjiakun.opinev0.R;

import java.util.List;

/**
 * Created by Wanjiakun on 3/22/2015.
 */
public class AppraisalAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ParseObject> appraisals;

    public AppraisalAdapter(Context c, List l) {
        Log.d("Adapter", "created");
        context = c;
        inflater = LayoutInflater.from(c);
        appraisals = l;
    }


    @Override
    public int getCount() {
        int count = 1;
        try{
            count = appraisals.size();
        }catch(NullPointerException e){
            Log.d("Adapter", "getting count error");
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.appraisal, parent, false);
        TextView subject = (TextView) view.findViewById(R.id.text_appraisal_subject);
        TextView message = (TextView) view.findViewById(R.id.text_appraisal_message);
        try {
            subject.setText(appraisals.get(position).getString("subject"));
            if(!appraisals.get(position).getBoolean("read")){
                subject.setTextAppearance(context, R.style.boldText);
            }
            String m = appraisals.get(position).getString("message");
            if(m.length() > 100){
                m = m.substring(0,100) + "...";
            }
            message.setText(m);
        }catch(NullPointerException e){
            Log.d("Inflate", "error caught");
        }

        view.setTag(position);
        return view;
    }
}
