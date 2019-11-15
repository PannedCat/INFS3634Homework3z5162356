package com.example.infs3604homework3;

import java.util.ArrayList;

public class Image
{
    private String width;

    private String id;

    private String url;

    private ArrayList<Breed> breed;

    private String height;

    public String getWidth ()
    {
        return width;
    }

    public void setWidth (String width)
    {
        this.width = width;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public ArrayList<Breed> getBreeds ()
    {
        return breed;
    }

    public void setBreeds (ArrayList<Breed> breeds)
    {
        this.breed = breeds;
    }

    public String getHeight ()
    {
        return height;
    }

    public void setHeight (String height)
    {
        this.height = height;
    }


}
