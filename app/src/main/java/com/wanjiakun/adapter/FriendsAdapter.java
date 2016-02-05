package com.wanjiakun.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.wanjiakun.opinev0.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Wanjiakun on 3/15/2015.
 */
public class FriendsAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private Session session;
    private String[][] friends;
    private List<Bitmap> photos;

    public FriendsAdapter (Context c, Session s, String[][] f, List<Bitmap> p){
        friends = f;
        photos = p;
        session = s;
        context = c;
        inflater = LayoutInflater.from(c);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public int getCount() {
        Log.d("Adapter", "getting count");
        int count = 1;
        try{
            count = friends.length;
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
        LinearLayout view;
        view = (LinearLayout)inflater.inflate(R.layout.friend, parent, false);
        view.setTag(position);
        final PhotoSquare photo =  (PhotoSquare) view.findViewById(R.id.image_profile);
        photo.setImageBitmap(photos.get(position));
        final TextView name = (TextView) view.findViewById(R.id.text_friend_name);
        name.setText(friends[position][0]);
        return view;
    }
}
