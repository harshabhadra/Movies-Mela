package com.example.android.moviesmela.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.moviesmela.MovieItem;
import com.example.android.moviesmela.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private LayoutInflater inflater;
    private List<MovieItem> videoList;
    private OnItemClickListener clickListener;
    String videoCode;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public VideoAdapter(Context context,OnItemClickListener listener, List<MovieItem> videoList) {

        inflater = LayoutInflater.from(context);
        clickListener = listener;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(inflater.inflate(R.layout.videos_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        holder.videoName.setText(videoList.get(position).getDataName());
        videoCode = videoList.get(position).getDataContent();
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public String getVideoCode(int postion) {
        return videoList.get(postion).getDataContent();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView videoName;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoName = itemView.findViewById(R.id.video_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickListener.onItemClick(position);
        }
    }
}
