package com.example.burakaybihomework3cs310;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    List<NewsItem> newsItem;
    Context context;
    NewsItemClickListener listener;

    public NewsAdapter(List<NewsItem> newsItem, Context context, NewsItemClickListener listener) {
        this.newsItem = newsItem;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.news_row_layout,parent, false);


        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {

        holder.txtDate.setText(new SimpleDateFormat("dd/MM/yyy").format(newsItem.get(position).getNewsDate()));
        holder.txtTitle.setText(newsItem.get(position).getTitle());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.newItemClicked(newsItem.get(position));
            }
        });

        if(newsItem.get(position).getBitmap()==null){
            new ImageDownloadTask(holder.imgNews).execute(newsItem.get(position));
        }
        else{
            holder.imgNews.setImageBitmap(newsItem.get(position).getBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return newsItem.size();
    }

    public interface NewsItemClickListener{
        public void newItemClicked(NewsItem selectedNewsItem);
    }


    class NewsViewHolder extends RecyclerView.ViewHolder{

        ImageView imgNews;
        TextView txtTitle;
        TextView txtDate;
        ConstraintLayout root;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.imgnews);
            txtTitle = itemView.findViewById(R.id.txtlisttitle);
            txtDate = itemView.findViewById(R.id.txtlistdate);
            root = itemView.findViewById(R.id.container);

        }


    }


}
