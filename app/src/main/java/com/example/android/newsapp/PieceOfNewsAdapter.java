package com.example.android.newsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PieceOfNewsAdapter extends ArrayAdapter {

    private List list;

    public PieceOfNewsAdapter(@NonNull Activity context, @NonNull ArrayList<PieceOfNews> myNews) {

        super(context, 0, myNews);
        list = myNews;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public android.view.View getView(final int position, @Nullable View convertView, @NonNull android.view.ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_element, parent, false);
        }

        // Gets the {@link Project} object located at this position in the list
        final PieceOfNews currentNews = (PieceOfNews) getItem(position);

        //gets the Title TV and sets a value on it
        TextView titleTV = listItemView.findViewById(R.id.titleTV);
        titleTV.setText(currentNews.getTitle());

        //gets the trailText TV and sets a value on it
        TextView trailTextTV = listItemView.findViewById(R.id.trailTextTV);
        if (!currentNews.getTrailText().equals(""))
            trailTextTV.setText(currentNews.getTrailText());
        else
            trailTextTV.setVisibility(View.INVISIBLE);

        //gets the TV for Pillar and Section and sets a value on it
        TextView pillarSectionTv = listItemView.findViewById(R.id.pillarSectionTV);
        pillarSectionTv.setText(currentNews.getPillarandSectionName());

        //gets the author TV and sets a value on it
        TextView AuthorTV = listItemView.findViewById(R.id.authorTV);
        if (currentNews.getAuthor() != null)
            AuthorTV.setText(currentNews.getAuthor());
        else
            // if non author is specified for the Piec of News, the AuthorTV is hidden
            AuthorTV.setVisibility(View.GONE);

        //gets the Date TV and sets a value on it
        TextView dateTV = listItemView.findViewById(R.id.dateTV);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        String myDate = formatter.format(currentNews.getDate());
        dateTV.setText(myDate);

        //Sets an onClickListener to open the webpage of the news
        if (!currentNews.getWeburl().equals(" ")) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW);
                    Uri webpage = Uri.parse(currentNews.getWeburl());
                    webIntent.setData(webpage);
                    // Checks if an app to handle the intent exists
                    PackageManager packageManager = getContext().getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;

                    // If at least a proper App exists, the Activity is started
                    if (isIntentSafe) {
                        getContext().startActivity(webIntent);
                    }
                }

            });
        }

        return listItemView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
