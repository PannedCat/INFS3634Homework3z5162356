package com.example.infs3604homework3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavouritesRecyclerAdapter extends RecyclerView.Adapter<FavouritesRecyclerAdapter.FavouriteViewHolder> {

    private List<String> favouriteArrayList;

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        public TextView favouriteTextView;


        public FavouriteViewHolder(@NonNull View v) {
            super(v);
            favouriteTextView = v.findViewById(R.id.favouriteName);
        }
    }

    public FavouritesRecyclerAdapter(List<String> breedNameList) {
        favouriteArrayList = breedNameList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favouritelayout, parent, false);
        FavouriteViewHolder favouriteViewHolder = new FavouriteViewHolder(view);
        return favouriteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        holder.favouriteTextView.setText(favouriteArrayList.get(position));


    }

    @Override
    public int getItemCount() {
        return favouriteArrayList.size();
    }
}
