package com.example.android.moviesmela.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moviesmela.Model.MovieItem;
import com.example.android.moviesmela.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private LayoutInflater inflater;
    private List<MovieItem>reviewList;

    public ReviewAdapter(Context context, List<MovieItem> reviewList) {
        inflater = LayoutInflater.from(context);
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(inflater.inflate(R.layout.review_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        holder.reviewerTextView.setText(reviewList.get(position).getDataName());
        holder.contentTextView.setText(reviewList.get(position).getDataContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView reviewerTextView;
        TextView contentTextView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewerTextView = itemView.findViewById(R.id.reviews_name);
            contentTextView = itemView.findViewById(R.id.review_content);
        }
    }
}
