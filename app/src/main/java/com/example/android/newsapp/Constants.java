package com.example.android.newsapp;

public final class Constants {

    /**
     * Constant value for the News loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    public static final int NEWS_LOADER_ID = 1;

    /**
     * a Strings containing the URL to call the Guardian API to retrieve the needed data and the APIKEY
     * Please insert your own api-key
     * Changing the parameters of the query may produce errors in the app, in particular in the extractNews method and in the PieceOfNewsAdapter
     */
    public final static String GUARDIANAPIURL = "https://content.guardianapis.com/search?show-fields=trailText&show-tags=contributor";
    public final static String APIKEY = "test";

}
