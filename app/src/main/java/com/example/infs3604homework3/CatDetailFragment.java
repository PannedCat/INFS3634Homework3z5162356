package com.example.infs3604homework3;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class CatDetailFragment extends Fragment {
    public HashMap<String, Breed> breedInfo = new HashMap<>();
    public ArrayList<Breed> breedArrayList;




    public CatDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_cat_detail, container, false);

        final Button favouriteButton = rootView.findViewById(R.id.addFavourite);

        final ImageView catImage = rootView.findViewById(R.id.catImage);

        final TextView catName = rootView.findViewById(R.id.catName);
        final TextView catDescription = rootView.findViewById(R.id.catDescription);
        final TextView catWeight = rootView.findViewById(R.id.catWeight);
        final TextView catTemperament = rootView.findViewById(R.id.catTemperament);
        final TextView catOrigin = rootView.findViewById(R.id.catOrigin);
        final TextView catLifespan = rootView.findViewById(R.id.catLifespan);
        final TextView catLink = rootView.findViewById(R.id.catLink);
        final TextView catFriendliness =  rootView.findViewById(R.id.catFriendliness);
        final TextView noCatText =  rootView.findViewById(R.id.noCatText);

        // Below is the code for fetching a bundle of data (Unique Breed ID) passed to this CatDetailFragment when it is swapped
        // in after the user selects a cat on the
        Bundle bundle = this.getArguments();
        final String catID = bundle.getString("CatID");

        // Below is the Volley code for fetching image data for each cat breed
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://api.thecatapi.com/v1/images/search?breed_id=" + catID;

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                // The URL returns an array of Image objects
                Image[] imageArray = gson.fromJson(response, Image[].class);
                // A check to make sure that the image of the cat exists
                if (!(imageArray.length == 0)) {
                    // The Image objects returned by the above URL are singular when searching with the entire breed ID
                    String imageURL = imageArray[0].getUrl();
                    Glide.with(getContext()).load(imageURL).into(catImage);
                }
                else catImage.setImageResource(R.drawable.nocat);
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

        // Below is the Volley code for returning Breed data, such as name, weight etc.
        String breedUrl = "https://api.thecatapi.com/v1/breeds";
        Response.Listener<String> breedResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type breedListType = new TypeToken<ArrayList<Breed>>(){}.getType();
                breedArrayList = gson.fromJson(response, breedListType);

                // Searching a HashMap based on a unique identifier (the breed ID passed from the Search Fragment) is simpler than
                // searching through an ArrayList
                for (int i = 0; i < breedArrayList.size(); i++) {
                    breedInfo.put(breedArrayList.get(i).getId(), breedArrayList.get(i));
                }
                // Catching any NullPointerExceptions in case the selected Breed of cat has missing information
                // and then adjusting a textView and Button visibility

                try {
                    catName.setText(breedInfo.get(catID).getName());
                    catDescription.setText("Description: " + breedInfo.get(catID).getDescription());
                    catWeight.setText("Weight: " + breedInfo.get(catID).getWeight().getMetric());
                    catTemperament.setText("Temperament: " + breedInfo.get(catID).getTemperament());
                    catOrigin.setText("Origin: " + breedInfo.get(catID).getOrigin());
                    catLifespan.setText("Lifespan: " + breedInfo.get(catID).getLife_span());
                    catLink.setText("Wikipedia Link: " + breedInfo.get(catID).getWikipedia_url());
                    catFriendliness.setText("Dog Friendliness Level: " + breedInfo.get(catID).getDog_friendly());
                }
                catch (Exception e) {
                    noCatText.setText(R.string.NoCatInfo);
                    favouriteButton.setVisibility(rootView.INVISIBLE);
                }
            }
        };
        Response.ErrorListener breedErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("The request failed.");
            }
        };

        StringRequest breedDataRequest =
                new StringRequest(Request.Method.GET, breedUrl, breedResponseListener, breedErrorListener);


        queue.add(breedDataRequest);

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Every time the button is clicked, add the favourite breed's unique ID to a static ArrayList in FavouriteDatabase
                FavouriteDatabase fd = new FavouriteDatabase();
                fd.addToFavouritesList(catID);
                Toast.makeText(getContext(), "Added to your Favourites", Toast.LENGTH_SHORT).show();
            }
        });





        return rootView;
    }

}
