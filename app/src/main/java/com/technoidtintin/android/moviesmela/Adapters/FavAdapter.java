package com.technoidtintin.android.moviesmela.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.FavlistItem;
import com.technoidtintin.android.moviesmela.R;

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
        return new FavViewHolder(inflater.inflate(R.layout.fav_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {

        holder.movieTv.setText(favlistItems.get(position).getMovieName());
        holder.releaseTv.setText(favlistItems.get(position).getReleaseDate());
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
        TextView movieTv;
        TextView releaseTv;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fav_image);
            movieTv = itemView.findViewById(R.id.fav_movie_name);
            releaseTv = itemView.findViewById(R.id.fav_release);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            favItemClickListener.onFavItemClick(position);
        }
    }
}
