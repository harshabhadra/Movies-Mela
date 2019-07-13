package com.example.android.moviesmela.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moviesmela.Model.FavlistItem;
import com.example.android.moviesmela.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private LayoutInflater inflater;
    private List<FavlistItem> favlistItems;
    private onFavItemClickListener favItemClickListener;

    public FavAdapter(Context context, onFavItemClickListener listener) {

        inflater = LayoutInflater.from(context);
        favItemClickListener = listener;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {

        Picasso.get().load(favlistItems.get(position).getMoviePosterUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return favlistItems.size();
    }

    public FavlistItem getFavMovie(int position) {
        if (favlistItems != null) {
            return favlistItems.get(position);
        }
        return null;
    }

    List<FavlistItem> getFavlistItems() {
        if (favlistItems != null) {
            return favlistItems;
        }
        return null;
    }

    public void setFavlistItems(List<FavlistItem> favlistItems) {
        this.favlistItems = favlistItems;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        favlistItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, favlistItems.size());
    }

    public interface onFavItemClickListener {
        void onFavItemClick(int position);
    }

    public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            favItemClickListener.onFavItemClick(position);
        }
    }
}
