package com.example.android.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving news Data from TheGuardian API.
 */
public final class ConnectionUtils {

    /**
     * Create a private constructor because no one should ever create a {@link ConnectionUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private ConnectionUtils() {
    }

    /*    */

    /**
     * Return a list of {@link PieceOfNews} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<PieceOfNews> extractNews(String myNewsJSON) {

        // Create an empty ArrayList that we can start adding News to
        ArrayList<PieceOfNews> news = new ArrayList<>();

        // Try to parse the myNewsJSON. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(myNewsJSON);
            JSONObject newBaseJsonResponse = baseJsonResponse.getJSONObject("response");
            JSONArray newsArray = newBaseJsonResponse.getJSONArray("results");
            String mTitle;
            String mTrailText = "";
            String mWeburl;
            String mPillarName;
            String mSectionName;
            String mDateString;
            Date mDate;

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);
                JSONObject fieldsObject = currentNews.getJSONObject("fields");

                mTitle = currentNews.getString("webTitle");
                // Since trailText is an optional field, we add a check on the Onbject
                if (fieldsObject != null)
                    mTrailText = fieldsObject.getString("trailText");
                mPillarName = currentNews.getString("pillarName");
                mSectionName = currentNews.getString("sectionName");
                mWeburl = currentNews.getString("webUrl");
                mDateString = currentNews.getString("webPublicationDate").substring(0, 10);
                mDate = Utility.convertStringToDate(mDateString, "yyyy-MM-dd");
                //mTitle = "News number " + String.valueOf(i) ;

                news.add(new PieceOfNews(mTitle, mTrailText, mWeburl, mPillarName, mSectionName, mDate));
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of news
        return news;
    }

    /**
     * Returns a new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl) {
        URL url;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("Error", "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url, Context context) throws IOException {

        // Introduces a delay on the Thread, for study purpose only,
        // It makes easier to check the behaviour of the app
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String jsonResponse = "";
        int ResponseCode;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            ResponseCode = urlConnection.getResponseCode();
            if (ResponseCode == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                jsonResponse = "";
            }
            Log.e("My error", "Wrong Connection result" + String.valueOf(ResponseCode));
        } catch (IOException e) {
            Log.e("My error", "IOException HTTPrequest" + e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Checks wether an internet connection is available on the device
     *
     * @param context
     * @return isConnected, true or false
     */
    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
