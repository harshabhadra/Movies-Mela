package com.example.android.moviesmela.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moviesmela.MovieItem;
import com.example.android.moviesmela.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private LayoutInflater inflater;
    private List<MovieItem> movieItems;
    private OnItemClickLisetener itemClickLisetener;

    public interface OnItemClickLisetener{
        void onItemClick(int position);
    }

    public MovieAdapter(Context context,OnItemClickLisetener lisetener,  List<MovieItem> movieItems) {

        inflater = LayoutInflater.from(context);
        itemClickLisetener = lisetener;
        this.movieItems = movieItems;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MovieViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Picasso.get().load(movieItems.get(position).getImageUrl()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }

     public MovieItem getMovieitem(int position){
        if (movieItems!= null){
            return movieItems.get(position);
        }
        return null;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            itemClickLisetener.onItemClick(position);
        }
    }
}
