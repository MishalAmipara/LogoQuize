package com.example.logoquize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;
    Button button[] = new Button[16];
    ArrayList<String> listImages;
    int pos = 0;
    MyAdapter myAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.pager);

        String[] images = new String[0];
        try {
            images = getAssets().list("pre");
            listImages= new ArrayList<String>(Arrays.asList(images));


        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        myAdapter=new MyAdapter(this,listImages);
        viewPager.setAdapter(myAdapter);
    }
}