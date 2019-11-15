package com.example.infs3604homework3;

import java.util.ArrayList;


// Uh this class is a fake database, containing an public static ArrayList for storing the unique ID of the favourites of the user.
public class FavouriteDatabase {

    public static ArrayList<String> favouriteArrayList = new ArrayList<>();

    public static void addToFavouritesList(String breedID) {
        favouriteArrayList.add(breedID);
    }
    public static ArrayList<String> getFavouritesList() {
        return favouriteArrayList;
    }



}
