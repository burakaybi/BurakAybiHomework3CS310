package com.example.burakaybihomework3cs310;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    ProgressDialog prgDialog;
    RecyclerView comRecView;
    List<CommentItem> comment;
    CommentsAdapter adp;
    int com_id;
    TextView txtname, txtcom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        comment = new ArrayList<>();


        getSupportActionBar().setTitle("Comments");

        //CommentItem comment_id = (CommentItem) getIntent().getSerializableExtra("selected");
        //String comment_id = getIntent().getExtras().getString("selected");
        com_id = getIntent().getExtras().getInt("selected");



        comRecView = findViewById(R.id.comrec);
        adp = new CommentsAdapter(comment, this, this );
        comRecView.setLayoutManager(new LinearLayoutManager(this));
        comRecView.setAdapter(adp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        CommentTask csk = new CommentTask();
        csk.execute("http://94.138.207.51:8080/NewsApp/service/news/getcommentsbynewsid/"+com_id);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        else if(item.getItemId() == R.id.c_add){
            Intent i = new Intent(this  ,PostCommentActivity.class);
            i.putExtra("comment",com_id);
            startActivity(i);
        }

        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CommentTask csk = new CommentTask();
        csk.execute("http://94.138.207.51:8080/NewsApp/service/news/getcommentsbynewsid/" + com_id);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    class CommentTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(CommentActivity.this);
            prgDialog.setTitle("Loading");
            prgDialog.setMessage("Please wait...");
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlStr = strings[0];
            StringBuilder buffer = new StringBuilder();

            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            comment.clear();
            try {
                JSONObject obj = new JSONObject(s);

                if (obj.getInt("serviceMessageCode") == 1) {

                    JSONArray arr = obj.getJSONArray("items");

                    for (int i = 0; i < arr.length(); i++) {

                        JSONObject current = (JSONObject) arr.get(i);

                        CommentItem item = new CommentItem(current.getInt("id"),
                                current.getString("name"),
                                current.getString("text")
                        );
                        comment.add(item);
                    }
                } else {

                }

                adp.notifyDataSetChanged();
                prgDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
