package com.example.testloginfb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testloginfb.R;
import com.example.testloginfb.pojos.SingleHorizontal;

public class ShowNewsActivity extends AppCompatActivity {

    private TextView title,description,pubDate;
    private ImageView image;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        initialView();
        Intent intent = getIntent();
        SingleHorizontal item = (SingleHorizontal) intent.getSerializableExtra("item");
        if(item !=null){
            initialData(item);
        }
    }

    private void initialData(SingleHorizontal item) {
        description.setText(item.getDesc());
        title.setText(item.getTitle());
        pubDate.setText(item.getPubDate());
        image.setImageResource(item.getImages());
    }

    private void initialView() {
        title = (TextView) findViewById(R.id.news_title);
        description = (TextView) findViewById(R.id.news_description);
        pubDate = (TextView) findViewById(R.id.news_published_date);
        image = (ImageView) findViewById(R.id.news_image_view);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Tạo đơn chuyển kho");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
