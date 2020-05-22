package com.example.burakaybihomework3cs310;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>  {
    List<CommentItem> comment;
    Context context;
    CommentActivity commentactivity;
    public CommentsAdapter(List<CommentItem> comment,CommentActivity commentactivity, Context context) {
        this.comment = comment;
        this.context = context;
        this.commentactivity = commentactivity;

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comments_row_layout,parent,false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, final int position) {

        holder.comTxt.setText(comment.get(position).getMessage());
        holder.comName.setText(comment.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return comment.size();
    }


    public interface CommentItemClickListener{
        public void newCommentClicked(CommentItem a);
    }


    class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView comName, comTxt;
        ConstraintLayout root;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comName = itemView.findViewById(R.id.txtname);
            comTxt = itemView.findViewById(R.id.txtcom);
            root = itemView.findViewById(R.id.container_c);
        }
    }
}
