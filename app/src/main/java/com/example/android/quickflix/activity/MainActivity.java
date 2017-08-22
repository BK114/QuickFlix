package com.example.android.quickflix.activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.quickflix.R;
import com.example.android.quickflix.adapter.MovieAdapter;
import com.example.android.quickflix.api.ApiInterfaceService;
import com.example.android.quickflix.api.RetrofitClient;
import com.example.android.quickflix.model.MovieResponse;
import com.example.android.quickflix.model.Movies;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    //query that we want [http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=[API_KEY]
    /**
     * Set the BASE_URL to be used for querying The Movie Database. API_KEY will be required
     * retrofit moved to its own class in order to separate concerns. Call activity_main on load
     * which will launch user directly into mostPopular recyclerView.
     * Create a list of Movie objects so recyclerView can populate the results
     * retrieved from Retrofits call to The Movie Database
     */

    private final static String TAG = MainActivity.class.getSimpleName();
    private final String API_KEY = "YOUR_API_KEY_HERE";
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        List<Movies> movies = new ArrayList<>();
        MovieAdapter adapter = new MovieAdapter(movies, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //cast layout id recycler_view into RecyclerView then set fixed size
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //get the current context of this recyclerView and adjust amount of columns based on screen orientation
        if (recyclerView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else if (recyclerView.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //Check to see if API_KEY

        //initialize the MainActivity with MostPopular Movies
        connectGetMostPopular();
    }

    private void connectGetMostPopular() throws IllegalFormatException {
        // Create an instance of ApiInterfaceService
        //Connect and get Most Popular Movies
        ApiInterfaceService movieApiService = RetrofitClient.getRetrofit().create(ApiInterfaceService.class);
        Call<MovieResponse> call = movieApiService.getMostPopularMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            /*
             * get a response from Retrofit and create a new MovieAdapter object that takes
             * in movies and context. set adapter to the recycelerView with passed parameters
             * set the title of the results to match the searched content.
             */

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                try {
                    List<Movies> movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(movies, getApplicationContext()));
                    recyclerView.smoothScrollToPosition(0);
                    setTitle("Most Popular");
                    Log.d(TAG, "Number of movies received: " + movies.size());
                    progressDialog.dismiss();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    setContentView(R.layout.activity_main);
                    Toast.makeText(getApplicationContext(), "Please Obtain an API KEY", Toast.LENGTH_SHORT).show();

                }
            }

            /*
             * pass a toast to let the user know to check the device connectivity.
             */
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                Toast.makeText(MainActivity.this, "Please check your connection...", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    /*
     * connect to The Movie Database utilizing Retrofit REST client
     * to retrive JSON. Retrofit comes with its own AsyncTask
     */
    private void connectGetTopRated() throws IllegalFormatException {
        // Create an instance of ApiInterfaceService
        //Connect and get Most top rated movies
        ApiInterfaceService movieApiService = RetrofitClient.getRetrofit().create(ApiInterfaceService.class);
        Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            /*
             * get a response from Retrofit and create a new MovieAdapter object that takes
             * in movies and context. set adapter to the recycelerView with passed parameters
             * set the title of the results to match the searched content.
             */
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    List<Movies> movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(movies, getApplicationContext()));
                    recyclerView.smoothScrollToPosition(0);
                    setTitle("Top Rated");
                    Log.d(TAG, "Number of movies received: " + movies.size());
                    progressDialog.dismiss();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    setContentView(R.layout.activity_main);
                    Toast.makeText(getApplicationContext(), "Please Obtain an API KEY", Toast.LENGTH_LONG).show();
                }
            }

            /*
             * pass a toast to let the user know to check the device connectivity.
             */
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Please check your connection...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * inflate the options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    /*
     * created a method for displaying most popular
     * or top rated movies by selecting options from
     * menu items
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                //get the most popular movies
                this.connectGetMostPopular();
                break;

            case R.id.action_top_rated:
                //get the top rated movies
                this.connectGetTopRated();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}