package com.example.logoquize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    ImageView imgView;
    Button button[] = new Button[16];
    ArrayList<String> listImages;
    int pos = 0;
    MyAdapter myAdapter;
    ViewPager viewPager;
    String[] ansarr;
    ArrayList<String> arrayList=new ArrayList<>();
    String finalans;
    char ans_chararr[];
    Button ans_button[];
    int t=0;
    LinearLayout linearLayout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<String> ansList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.pager);
        linearLayout=findViewById(R.id.linear);
        //imgView=findViewById(R.id.img);
        String[] images = new String[0];
        try {
            images = getAssets().list("pre");
            listImages= new ArrayList<String>(Arrays.asList(images));
            System.out.println("List of images="+listImages);

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        myAdapter=new MyAdapter(this,listImages);
        viewPager.setAdapter(myAdapter);
        create(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    create(position);
                    t=0;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void create(int position) {
        pos = position;
        arrayList.clear();
        linearLayout.removeAllViews();
        imgView=findViewById(R.id.img);
        //public static int t=0;
        System.out.println("instanciate object");
        for (int i = 0; i < button.length; i++) {
            int id = getResources().getIdentifier("b" + i, "id", getPackageName());
            button[i] = findViewById(id);
        }
        System.out.println("ans=" + listImages.get(position));
        ansarr = listImages.get(position).split("\\.");
        System.out.println("ansArr=" + ansarr[0] + "\tPosition=" + position);
        finalans = ansarr[0];
        System.out.println("Final Ans=" + finalans);
        ans_button = new Button[finalans.length()];
        ans_chararr = finalans.toCharArray();
        for (int i = 0; i < listImages.size(); i++) {
            //System.out.println("Imgs=" + listImages.get(i));
        }
        for (int i = 0; i < finalans.length(); i++) {
            arrayList.add(""+ans_chararr[i]);
        }
        for (int i = 0; i < button.length - finalans.length(); i++) {
            Random r = new Random();
            char c = (char) (r.nextInt(26) + 'a');
            arrayList.add("" + c);
            System.out.println("ArrayList[" + i + "]=" + arrayList.get(i));
        }
        Collections.shuffle(arrayList);
        for (int i = 0; i < button.length; i++) {
            button[i].setText("" + arrayList.get(i));
            button[i].setOnClickListener(this);
        }
        for (int i = 0; i < finalans.length(); i++) {
            ans_button[i] = new Button(MainActivity.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            layoutParams.setMargins(5, 5, 5, 5);
            ans_button[i].setLayoutParams(layoutParams);
            ans_button[i].setBackgroundColor(getResources().getColor(R.color.purple_200));
            linearLayout.addView(ans_button[i]);
        }
    }
    @Override
    public void onClick(View view)
    {
        System.out.println("FinalAns="+finalans);
        for (int i = 0; i < button.length; i++)
        {
            if (button[i].getId() == view.getId())
            {
                if (!button[i].getText().toString().isEmpty())
                {
                    if (t < finalans.length())
                    {
                        ans_button[t].setText(button[i].getText());
                        button[i].setText("");
                        t++;
                    }
                }
            }
        }
        String ans=String.valueOf(ans_button);
        preferences=getSharedPreferences("myPref",MODE_PRIVATE);
        editor=preferences.edit();
        if (ans.equals(finalans)) {
            editor.putString("matched"+pos,"true");
            editor.commit();

        }
        else if(!ans.equals(finalans))
        {
            editor.putString("matched"+pos,"false");
            editor.commit();

        }
    }
}
