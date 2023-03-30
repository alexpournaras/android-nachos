package com.alexpournaras.nachos.adapters;

import android.content.Context;
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

public class SliderPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Movie> sliderMovies;

    public SliderPagerAdapter(Context context, List<Movie> sliderMovies) {
        this.context = context;
        this.sliderMovies = sliderMovies;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider_movie, container, false);
        ImageView sliderImage = view.findViewById(R.id.sliderImage);
        TextView sliderTitle = view.findViewById(R.id.sliderTitle);
        TextView sliderRatingText = view.findViewById(R.id.sliderRatingText);

        Movie movie = sliderMovies.get(position % sliderMovies.size());
        sliderImage.setImageResource(R.drawable.slider_batman);
        sliderTitle.setText(movie.getTitle());
        sliderRatingText.setText("75% Liked this movie!");

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
