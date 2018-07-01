package com.example.android.newsapp;

import java.util.Date;

/**
 * Created by RutiglianoL on 17/06/2018.
 * Represents a News published by a website
 */
public class PieceOfNews {

    private String title;
    // trailText is a short text to introduce the news under the title
    private String trailText;
    private String author;
    private String weburl;
    private String pillarName;
    private String sectionName;
    private Date date;

    /**
     * Constructs a new PieceOfNews with  values for title, weburl, pillarName, sectionName, date
     */
    public PieceOfNews(String mTitle, String mTrailText, String mAuthor, String mWeburl, String mPillarName, String mSectionName, Date mDate) {

        title = mTitle;
        trailText = mTrailText;
        author = mAuthor;
        weburl = mWeburl;
        pillarName = mPillarName;
        sectionName = mSectionName;
        date = mDate;
    }

    public String getTitle() {
        return title;
    }

    public String getTrailText() {
        return trailText;
    }

    public String getAuthor() {
        return author;
    }

    public String getWeburl() {
        return weburl;
    }

    public Date getDate() {
        return date;
    }

    /**
     * returns a concatenated String for pillar and Section Name
     */
    public String getPillarandSectionName() {
        // if pillar and section are equal, returns just one value of the two
        if (pillarName.equals(sectionName))
            return pillarName;
        else
            return (pillarName + "/" + sectionName);
    }
}
