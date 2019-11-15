package com.example.infs3604homework3;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment {
    public ArrayList<String> idArrayList;
    public List<Breed> breedList;
    public List<String> favouriteArrayList;

    private RecyclerView.LayoutManager favouriteLayoutManager;
    private RecyclerView.Adapter favouriteAdapter;



    public FavouritesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_favourites, container, false);



        final RecyclerView favouriteRecycler = rootView.findViewById(R.id.favouriteRecyclerVIew);
        favouriteLayoutManager = new LinearLayoutManager(rootView.getContext());
        favouriteRecycler.setLayoutManager(favouriteLayoutManager);

        // Populating an ArrayList<String> with the favourite data from FavouriteDatabase, calling the getFavouriteNames method
        idArrayList = FavouriteDatabase.getFavouritesList();
        favouriteArrayList = getFavouriteNames(idArrayList);

        favouriteAdapter = new FavouritesRecyclerAdapter(favouriteArrayList);
        favouriteRecycler.setAdapter(favouriteAdapter);

        return rootView;
    }

    public List<String> getFavouriteNames(final ArrayList<String> idArrayList) {
        ArrayList<String> favouriteList = new ArrayList<>();
        final BreedDatabase bd = BreedDatabase.getInstance(AppContext.getContext());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String breedUrl = "https://api.thecatapi.com/v1/breeds";
        Response.Listener<String> breedResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                
                Gson gson = new Gson();
                Type breedListType = new TypeToken<ArrayList<Breed>>(){}.getType();
                breedList = gson.fromJson(response, breedListType);

                // Inserting the ArrayList of Breed objects as obtained from the URL above into a Room database
                bd.breedDao().insert(breedList);

            }
        };
        Response.ErrorListener breedErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Breed data request failed.");
            }
        };

        StringRequest breedDataRequest =
                new StringRequest(Request.Method.GET, breedUrl, breedResponseListener, breedErrorListener);

        queue.add(breedDataRequest);

        // Filling up a favourite ArrayList<String> with Breed objects that correspond with the unique ID's in the idArrayList
        for (int i = 0; i < idArrayList.size(); i++) {
            favouriteList.add(bd.breedDao().getBreedName(idArrayList.get(i)));

        }
        return favouriteList;
    }

}
