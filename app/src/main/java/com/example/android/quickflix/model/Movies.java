package com.example.android.quickflix.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by brockrice on 8/4/17. Create the Movie Object and serialize the required
 * fields
 */


public class Movies implements Serializable {


    /*
     * Retrieve data from JSON returned by THe Movie Database
     * Ensure field names are the same as those in The Movie Database
     * we can use our own names within program.
     * Serialize each JSON parameter and match with Movies variables
     */
    @SerializedName("poster_path")
    private final String posterPath;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("release_date")
    private final String releaseDate;
    @SerializedName("original_title")
    private final String originalTitle;
    @SerializedName("vote_average")
    private final Double voteAverage;


    public Movies(String posterPath, String overview, String releaseDate,
                  String originalTitle, Double voteAverage) {
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

}