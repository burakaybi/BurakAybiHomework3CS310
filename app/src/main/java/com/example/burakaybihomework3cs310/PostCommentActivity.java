package com.example.burakaybihomework3cs310;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;

import org.json.JSONException;
import org.json.JSONObject;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PostCommentActivity extends AppCompatActivity {
    EditText cm_name, cm_txt;
    Button btn;
    int comment_id;
    String _news_id;
    ProgressDialog prgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_comment);

        comment_id = getIntent().getExtras().getInt("comment");

        cm_name = findViewById(R.id.com_name);
        cm_txt = findViewById(R.id.com_txt);
        btn = findViewById(R.id.com_btn);
        _news_id = Integer.toString(comment_id);

        Log.i("DEV", String.valueOf(comment_id));


        getSupportActionBar().setTitle("Post Comment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return true;
    }



    public void taskCallClicked(View v){

        JsonTask tsk = new JsonTask();
        String A = cm_name.getText().toString();
        String B = cm_txt.getText().toString();
        String C = String.valueOf(comment_id);

        if (cm_txt.getText().length()==0 || cm_txt.getText().length()==0)
        {

            FragmentManager fm = getSupportFragmentManager();
            PostDialog dialog = new PostDialog();
            dialog.show(fm,"");


        }

        else{


            tsk.execute("http://94.138.207.51:8080/NewsApp/service/news/savecomment",
                    A,
                    B,
                    C);

            finish();
        }


    }

    class JsonTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            prgDialog = new ProgressDialog(PostCommentActivity.this);
            prgDialog.setTitle("Loading");
            prgDialog.setMessage("Please wait...");
            prgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            prgDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder strBuilder = new StringBuilder();
            String urlStr = strings[0];
            String name = strings[1];
            String comment = strings[2];
            String newsid = strings[3];

            JSONObject obj = new JSONObject();
            try {
                obj.put("name",name);
                obj.put("text",comment);
                obj.put("news_id", newsid);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/json");
                conn.connect();

                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(obj.toString());


                if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line ="";

                    while((line = reader.readLine())!=null){
                        strBuilder.append(line);
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strBuilder.toString();
        }


    }





}
