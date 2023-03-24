package com.alexpournaras.nachos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Movie> sliderMovies;

    public SliderPagerAdapter(Context context, List<Movie> sliderMovies) {
        this.context = context;
        this.sliderMovies = sliderMovies;
    }

    @Override
    public int getCount() {
        return sliderMovies.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_item, container, false);
        ImageView sliderImage = view.findViewById(R.id.sliderImage);
        TextView sliderTitle = view.findViewById(R.id.sliderTitle);

        Movie movie = sliderMovies.get(position);
        sliderImage.setImageResource(R.drawable.slider_batman);
        sliderTitle.setText(movie.getTitle());

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