package com.example.android.newsapp;

public final class Constants {

    /**
     * Constant value for the News loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    public static final int NEWS_LOADER_ID = 1;

    /**
     * a String containing the URL to call the Guardian API to retrieve the needed data.
     * Please insert your own api-key
     * Changing the parameters of the query may produce errors in the app, in particular in the extractNews method and in the PieceOfNewsAdapter
     */
    public final static String GUARDIANAPIURL = "https://content.guardianapis.com/search?section=sport&show-fields=trailText&show-tags=contributor&page-size=50&api-key=test";

}
