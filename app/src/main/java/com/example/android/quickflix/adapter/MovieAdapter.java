package com.example.android.quickflix.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.quickflix.R;
import com.example.android.quickflix.activity.MovieDetailActivity;
import com.example.android.quickflix.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by brockrice on 8/4/17.
 *
 * this adapter will take Movie objects and set place the movie objects into a view holder
 * a ClickListener will be set with @verriden OnClick method in order to get the position of
 * the movie poster that was clicked and pass intent Extras into the proper fields and then to
 * the MovieDetailedActivity Class.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<Movies> movie;
    private final Context context;

    public MovieAdapter(List<Movies> movie, Context context) {
        this.movie = movie;
        this.context = context;
    }

    /* onCreateViewHolder(ViewGroup parent, int viewType) called right when the adapter
     * is created and is used to initialize ViewHolder. we will pass in a new MovieViewHolder
     * from a class created further below.
     */

    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_item, parent, false);
        return new MovieViewHolder(view);
    }

    /* onBinViewHolder will bind the MovieViewHolder object to the correct postion within the view.
     * It will also require a calle to The Movie Database in order to retrieve the correct image.
     * Picasso will be used to Fetch images and load them into Views
     */

    @Override
    public void onBindViewHolder(final MovieViewHolder viewHolder, final int position) {

        /* Set string to IMAGE_URL_BASE_PATH and get the poster position for each movie object.
         * Pass movie object position along with posterPath request to Picasso along with current context.
         * Load the proper image_url position into the poster image
         * This method is called for each ViewHolder to bind it to the adapter.
         * This is where we will pass our data to our ViewHolder.
         */
        String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w185/";
        String image_url = IMAGE_URL_BASE_PATH + movie.get(position).getPosterPath();
        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.andiron)
                .error(R.drawable.popcorn)
                .into(viewHolder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movie.size();
    }


    @Override
    public int getItemViewType(final int position) {
        return position;
    }

    //Get a reference to the views in the layout using their ID
    public class MovieViewHolder extends RecyclerView.ViewHolder {
        public final ImageView posterImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImage = (ImageView) itemView.findViewById(R.id.movie_image);

            itemView.setOnClickListener(new View.OnClickListener() {

            /**
             * After poster image is clicked, set a Movies object named clickedMovieItem to the
             *postion of the clicked movie poster. Pass extras into the intend and get the position
             * of each field in order to pass to the MovieDetailsActivity
             */

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Movies clickedMovieItem = movie.get(position);
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra("poster_path", movie.get(position).getPosterPath());
                    intent.putExtra("original_title", movie.get(position).getOriginalTitle());
                    intent.putExtra("release_date", movie.get(position).getReleaseDate());
                    intent.putExtra("overview", movie.get(position).getOverview());
                    intent.putExtra("vote_average", Double.toString(movie.get(position).getVoteAverage()));
                    intent.putExtra("movie", clickedMovieItem);

                    /* (Intent.FLAG_ACTIVITY_NEW_TASK) is required in order to
                     * make sure Android does not try to launch this Activity within the current
                     * task. A new task will be created.
                     */
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    }
                }
            });
        }
    }
}
