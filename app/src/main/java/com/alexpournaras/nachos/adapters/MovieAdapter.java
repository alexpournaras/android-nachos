package com.alexpournaras.nachos.adapters;

import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Random;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;
    private MovieItemClickListener movieItemClickListener;

    public MovieAdapter(Context context, List<Movie> movies, MovieItemClickListener movieItemClickListener) {
        this.context = context;
        this.movies = movies;
        this.movieItemClickListener = movieItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view, movieItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieTitle.setText(movie.getTitle());

        String posterImageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterImageUrl();
        Glide.with(context)
            .load(posterImageUrl)
            .placeholder(R.drawable.placeholder_vertical)
            .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieImage;
        TextView movieTitle;
        MovieItemClickListener movieItemClickListener;

        public ViewHolder(@NonNull View itemView, MovieItemClickListener movieItemClickListener) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movieImage);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            this.movieItemClickListener = movieItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            movieItemClickListener.onMovieItemClick(getAdapterPosition());
        }
    }

    public interface MovieItemClickListener {
        void onMovieItemClick(int position);
    }

    public void updateMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
}
