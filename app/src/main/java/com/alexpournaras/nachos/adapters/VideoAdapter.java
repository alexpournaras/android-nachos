package com.alexpournaras.nachos.adapters;

import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexpournaras.nachos.models.Video;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Random;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> videos;
    private Context context;

    public VideoAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.videoTitle.setText(video.getTitle());

        String videoThumbnailUrl = "https://img.youtube.com/vi/" + video.getYoutubeId() + "/hqdefault.jpg";
        Glide.with(context)
            .load(videoThumbnailUrl)
            .placeholder(R.drawable.placeholder_horizontal)
            .into(holder.videoThumbnail);

        holder.videoPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String youtubeUrl = "https://www.youtube.com/watch?v=" + video.getYoutubeId();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        ImageButton videoPlayButton;
        TextView videoTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.videoThumbnail);
            videoPlayButton = itemView.findViewById(R.id.videoPlayButton);
            videoTitle = itemView.findViewById(R.id.videoTitle);
        }
    }
}
