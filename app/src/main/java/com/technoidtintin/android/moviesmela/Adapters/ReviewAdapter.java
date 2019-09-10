package com.technoidtintin.android.moviesmela.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.technoidtintin.android.moviesmela.Model.MovieItem;
import com.technoidtintin.android.moviesmela.R;

import java.util.List;

import at.blogc.android.views.ExpandableTextView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private LayoutInflater inflater;
    private List<MovieItem> reviewList;

    public ReviewAdapter(Context context, List<MovieItem> reviewList) {
        inflater = LayoutInflater.from(context);
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(inflater.inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewViewHolder holder, int position) {

        holder.reviewerTextView.setText(reviewList.get(position).getDataName());
        holder.contentTextView.setText(reviewList.get(position).getDataContent());

        holder.contentTextView.setInterpolator(new OvershootInterpolator());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.contentTextView.isExpanded()) {
                    holder.contentTextView.collapse();
                    holder.button.setText("See More");
                } else {
                    holder.contentTextView.expand();
                    holder.button.setText("See less");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView reviewerTextView;
        ExpandableTextView contentTextView;
        Button button;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            button = this.itemView.findViewById(R.id.see_more_button);
            reviewerTextView = itemView.findViewById(R.id.reviews_name);
            contentTextView = this.itemView.findViewById(R.id.review_content);
        }
    }
}
