package com.example.infs3604homework3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder> {

    private ArrayList<Breed> breedArrayList;

    public static class HomeViewHolder extends RecyclerView.ViewHolder{
        public TextView resultText;
        public View searchView;


        public HomeViewHolder(@NonNull View v) {
            super(v);
            resultText = v.findViewById(R.id.resultTextView);
            searchView = v;

        }
    }

    public HomeRecyclerAdapter(ArrayList<Breed> breedList){
        breedArrayList = breedList;

    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resultlayout, parent, false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);

        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int position) {
        // Storing the unique ID of the selected breed/cat for transfer later to other fragments
        final String catName = breedArrayList.get(position).getName();
        final String catID = breedArrayList.get(position).getId();
        holder.resultText.setText(catName);

        holder.searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Using a Bundle to encapsulate data for transfer between two fragments
                    CatDetailFragment fragment = new CatDetailFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("CatID", catID);
                    fragment.setArguments(bundle);

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.homeBaseFrame, fragment)
                            .addToBackStack(null).commit();
            }
        });



    }



    @Override
    public int getItemCount() {
        return breedArrayList.size();
    }
}
