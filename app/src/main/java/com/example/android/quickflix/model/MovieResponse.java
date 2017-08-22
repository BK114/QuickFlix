package com.example.android.quickflix.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brockrice on 8/10/17.
 *
 * serialization of Movie Response object
 * used with retrofit to retrieve JSON data. More fields may
 * be added as app grows
 *
 */

public class MovieResponse implements Serializable {
    //serialize fields for the results that we need
    @SerializedName("results")
    private final List<Movies> results;

    public List<Movies> getResults() {
        return results;
    }
    public MovieResponse(List<Movies> results) {
        this.results = results;
    }

}

