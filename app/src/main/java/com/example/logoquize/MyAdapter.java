    package com.example.logoquize;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyAdapter extends PagerAdapter{
    ArrayList<String> listImages;
    Context context;
    Button[] button = new Button[16];
    String[] ansarr;
    String finalans;
    Button ans_button[] = new Button[0];
    char ans_chararr[];
    ArrayList arrayList = new ArrayList<>();
    int pos=0;
    public static int t=0;
    SharedPreferences preferences;
    public MyAdapter(Context context, ArrayList<String> listImages) {
        this.context = context;
        this.listImages = listImages;
         preferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, container, false);
        ImageView imageView = view.findViewById(R.id.img);
        String str=preferences.getString("matched"+position,"false");
        pos=position;
        InputStream inputstream = null;
        if(str.equals("false")) {

            try {
                inputstream = context.getAssets().open("pre/" + listImages.get(position));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Drawable drawable = Drawable.createFromStream(inputstream, null);
            imageView.setImageDrawable(drawable);
        }
        else if(str.equals("true"))
        {
            try {
                inputstream = context.getAssets().open("post/" + listImages.get(position));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Drawable drawable = Drawable.createFromStream(inputstream, null);
            imageView.setImageDrawable(drawable);
        }
        //createFun(position, linearLayout);
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
