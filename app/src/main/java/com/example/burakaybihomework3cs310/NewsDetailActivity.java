package com.example.burakaybihomework3cs310;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsDetailActivity extends AppCompatActivity {
    ImageView imgDetail;
    TextView DetailTitle, DetailTxt,detaildate;
    NewsAdapter adp2;
    int id_n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        DetailTitle = findViewById(R.id.TxtTitle);
        DetailTxt = findViewById(R.id.TxtDetail);
        imgDetail = findViewById(R.id.imgdetail);
        detaildate = findViewById(R.id.TxtDate);

        NewsItem detail_item = (NewsItem)getIntent().getSerializableExtra("selected_new");
        id_n = detail_item.getId();
        detaildate.setText(new SimpleDateFormat("dd/MM/yyy").format(detail_item.getNewsDate()));
        DetailTxt.setText(detail_item.getText());
        DetailTitle.setText(detail_item.getTitle());
        ImageDownloadTask tsk = new ImageDownloadTask(imgDetail);
        tsk.execute(detail_item);


        getSupportActionBar().setTitle("News Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_menu,menu);

        return true;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            //finish();
            onBackPressed();
        }

        else if (item.getItemId() == R.id.menu_add){


            Intent i = new Intent(NewsDetailActivity.this, CommentActivity.class);
            i.putExtra("selected",id_n);
            startActivity(i);


        }
        return true;
    }


}
