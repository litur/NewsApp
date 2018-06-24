package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class NewsLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<PieceOfNews>> {
    private static final String LOG_TAG = NewsLoader.class.getName();
    private String myURL;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        myURL = url;
    }

    @Nullable
    @Override
    public ArrayList<PieceOfNews> loadInBackground() {

        String myNews = null;
        ArrayList<PieceOfNews> myNewsArrayList;

        // Don't perform the request if there are no URLs, or the first URL is null.
        if ((myURL == null) || myURL.equals("")) {
            Log.e(LOG_TAG, "Received null or empty url");
            return null;
        }

        // Transforms the String of the url in a valid URL
        URL GuardianURL = ConnectionUtils.createUrl(myURL);

        // Don't perform the request if the URL is null.
        if (GuardianURL == null) {
            return null;
        }

        // Performs the HTTP request for news data and process the response.
        try {
            myNews = ConnectionUtils.makeHttpRequest(GuardianURL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extracts the data from the JSON data to an ArrayList of PieceOfNews
        myNewsArrayList = ConnectionUtils.extractNews(myNews);

        return myNewsArrayList;

    }
}
