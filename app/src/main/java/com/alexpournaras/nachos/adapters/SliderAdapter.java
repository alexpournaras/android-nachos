package com.alexpournaras.nachos.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

import com.alexpournaras.nachos.models.Movie;
import com.alexpournaras.nachos.R;
import com.bumptech.glide.Glide;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Movie> sliderMovies;

    public SliderAdapter(Context context, List<Movie> sliderMovies) {
        this.context = context;
        this.sliderMovies = sliderMovies;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider_movie, container, false);
        ImageView sliderImage = view.findViewById(R.id.sliderImage);
        TextView sliderTitle = view.findViewById(R.id.sliderTitle);
        TextView sliderRatingText = view.findViewById(R.id.sliderRatingText);

        if (sliderMovies.size() > 0) {
            Movie movie = sliderMovies.get(position % sliderMovies.size());

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/original" + movie.getPosterImageUrl())
                .placeholder(R.drawable.slider_batman)
                .error(R.drawable.slider_batman)
                .into(sliderImage);

            sliderTitle.setText(movie.getTitle());
            sliderRatingText.setText("75% Liked this movie!");
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
