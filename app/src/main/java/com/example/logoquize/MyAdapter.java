package com.example.logoquize;

import android.content.Context;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MyAdapter extends PagerAdapter implements View.OnClickListener {
    ArrayList<String> listImages;
    Context context;
    Button[] button = new Button[16];
    String[] ansarr;
    String finalans;
    Button ans_button[] = new Button[0];
    char ans_chararr[];
    ArrayList arrayList = new ArrayList<>();
    int t = 0;

    public MyAdapter(MainActivity context, ArrayList<String> listImages) {
        this.context = context;
        this.listImages = listImages;
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
        LinearLayout linearLayout = view.findViewById(R.id.linear);
        int pos = position;
        for (int i = 0; i < button.length; i++) {
            int id = context.getResources().getIdentifier("b" + i, "id", context.getPackageName());
            button[i] = view.findViewById(id);
        }
        InputStream inputstream = null;
        try {
            inputstream = context.getAssets().open("pre/" + listImages.get(pos));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Drawable drawable = Drawable.createFromStream(inputstream, null);
        imageView.setImageDrawable(drawable);
        System.out.println("ans=" + listImages.get(pos));
        ansarr = listImages.get(pos).split("\\.");
//            System.out.println("ansArr="+ansarr[pos]);
        finalans=ansarr[0];
        ans_button=new Button[finalans.length()];
        ans_chararr=finalans.toCharArray();
        for (int i = 0; i < listImages.size(); i++) {
            System.out.println("Imgs=" + listImages.get(i));
        }
        for (int i = 0; i < finalans.length(); i++) {
            arrayList.add(ans_chararr[i]);
        }
        for (int i = 0; i < button.length - finalans.length(); i++) {
            Random r = new Random();
            char c = (char) (r.nextInt(26) + 'a');
            arrayList.add("" + c);
        }
        Collections.shuffle(arrayList);
        for (int i = 0; i < button.length; i++) {
            button[i].setText("" + arrayList.get(i));
            button[i].setOnClickListener(this);
        }
        for (int i = 0; i < finalans.length(); i++) {
            ans_button[i] = new Button(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            layoutParams.setMargins(5, 5, 5, 5);
            ans_button[i].setLayoutParams(layoutParams);
            ans_button[i].setBackgroundColor(context.getResources().getColor(R.color.purple_200));
            linearLayout.addView(ans_button[i]);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < button.length; i++) {
            if (button[i].getId() == view.getId()) {
                if (!button[i].getText().toString().isEmpty()) {
                    if (t < finalans.length()) {
                        ans_button[t].setText(button[i].getText());
                        button[i].setText("");
                        t++;
                    }
                }
            }
        }
    }
}
