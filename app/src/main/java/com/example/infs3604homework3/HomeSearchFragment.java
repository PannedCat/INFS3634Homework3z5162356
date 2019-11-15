package com.example.infs3604homework3;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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


public class HomeSearchFragment extends Fragment {
    private RecyclerView.LayoutManager homeLayoutManager;
    private RecyclerView.Adapter homeAdapter;
    private ArrayList<Breed> breedArrayList;


    public HomeSearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home_search, container, false);

        final ImageButton searchButton = rootView.findViewById(R.id.resultsSearchButton);

        final EditText searchEditText = rootView.findViewById(R.id.searchEditView);

        final RecyclerView resultRecyclerView = rootView.findViewById(R.id.resultsRecyclerView);

        homeLayoutManager = new LinearLayoutManager(rootView.getContext());
        resultRecyclerView.setLayoutManager(homeLayoutManager);

        final RequestQueue queue = Volley.newRequestQueue(rootView.getContext());

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capturing and storing the text entered by the user as a search term, for later use in the search URL
                String query = searchEditText.getText().toString();
                String url = "https://api.thecatapi.com/v1/breeds/search?q=" + query;

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Type breedListType = new TypeToken<ArrayList<Breed>>(){}.getType();
                        breedArrayList = gson.fromJson(response, breedListType);

                        homeAdapter = new HomeRecyclerAdapter(breedArrayList);
                        resultRecyclerView.setAdapter(homeAdapter);

                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("The request failed.");
                    }
                };

                StringRequest stringRequest =
                        new StringRequest(Request.Method.GET, url, responseListener, errorListener);

                queue.add(stringRequest);

            }
        });

        return rootView;
    }

}
