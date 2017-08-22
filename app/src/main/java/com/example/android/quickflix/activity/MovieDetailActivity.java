package com.example.android.quickflix.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quickflix.R;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            ImageView posterImage = (ImageView) findViewById(R.id.detail_movie_image);
            TextView title = (TextView) findViewById(R.id.detail_movie_title);
            TextView releaseDate = (TextView) findViewById(R.id.detail_release_date);
            TextView overview = (TextView) findViewById(R.id.detail_overview);
            TextView rating = (TextView) findViewById(R.id.detail_rating);

            Intent intentFromInitiator = getIntent();

            if (intentFromInitiator.hasExtra("movie")) {

                String movieImage = getIntent().getExtras().getString("poster_path");
                String movieTitle = getIntent().getExtras().getString("original_title");
                String dateReleased = getIntent().getExtras().getString("release_date");
                String synopsis = getIntent().getExtras().getString("overview");
                String voterRating = getIntent().getExtras().getString("vote_average");

                String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w185/";
                String image_url = IMAGE_URL_BASE_PATH + movieImage;
                Picasso.with(this)
                        .load(image_url)
                        .placeholder(R.drawable.andiron)
                        .error(R.drawable.popcorn)
                        .into(posterImage);

                title.setText(movieTitle);
                releaseDate.setText(dateReleased);
                overview.setText(synopsis);
                rating.setText(voterRating);
            } else {
                Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}