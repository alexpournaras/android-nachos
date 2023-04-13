package com.alexpournaras.nachos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexpournaras.nachos.BuildConfig;
import com.alexpournaras.nachos.R;
import com.alexpournaras.nachos.models.Cast;
import com.bumptech.glide.Glide;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private List<Cast> cast;
    private Context context;

    public CastAdapter(Context context, List<Cast> cast) {
        this.context = context;
        this.cast = cast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cast castMember = cast.get(position);
        holder.castName.setText(castMember.getName());

        String imageUrl = BuildConfig.TMDB_IMAGE_URL + castMember.getImageUrl();
        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_vertical)
            .into(holder.castImage);

        holder.castImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, castMember.getCharacter(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView castImage;
        TextView castName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            castImage = itemView.findViewById(R.id.castImage);
            castName = itemView.findViewById(R.id.castName);
        }
    }
}
