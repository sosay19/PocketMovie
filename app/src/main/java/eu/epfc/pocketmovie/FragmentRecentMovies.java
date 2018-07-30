package eu.epfc.pocketmovie;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.epfc.pocketmovie.model.HttpRequestService;
import eu.epfc.pocketmovie.model.Movie;
import eu.epfc.pocketmovie.model.SavedMoviesManager;

public  class FragmentRecentMovies extends Fragment implements SWMoviesAdapter.ListItemClickListener {

    private List<Movie> movies;
    private static String TAG = "MainActivity";

    private HttpRequestService httpRequestService = new HttpRequestService();
    private SWMoviesAdapter SWMoviesAdapter;
    private int  urlStringId=1;

        @Override
    public void onListItemClick(int clickedItemIndex) {
                if(clickedItemIndex==20)
                {
                    ++urlStringId;
                    String urlString ="https://api.themoviedb.org/3/movie/popular?page="+urlStringId+"&api_key=ea2dcee690e0af8bb04f37aa35b75075";
                    HttpRequestService.startActionRequestHttp(getActivity().getApplicationContext(), urlString);
                }
                else if (clickedItemIndex==21)
                {

                    if(urlStringId>1)
                    {
                        System.out.println("urlStringId " + urlStringId);
                        --urlStringId;
                        String urlString ="https://api.themoviedb.org/3/movie/popular?page="+urlStringId+"&api_key=ea2dcee690e0af8bb04f37aa35b75075";
                        HttpRequestService.startActionRequestHttp(getActivity().getApplicationContext(), urlString);
                    }

                }
                else
                {
                    Movie selectedMovie = movies.get(clickedItemIndex);


                    Intent detailIntent = new Intent(getActivity(),DetailActivity.class);
                    detailIntent.putExtra("movieObject", selectedMovie);
                    startActivity(detailIntent);
                }




    }
    public FragmentRecentMovies() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_movies_fragment, container,
                false);

                HttpReceiver httpReceiver = new HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        intentFilter.addAction("httpRequestFailed");
        getActivity().registerReceiver(httpReceiver,intentFilter);
        SavedMoviesManager.getInstance().initWithContext(getActivity().getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) view
                .findViewById(R.id.list_movies);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        SWMoviesAdapter = new SWMoviesAdapter(this);

        recyclerView.setAdapter(SWMoviesAdapter);
        SWMoviesAdapter.setMovieDatas(movies);
        SWMoviesAdapter.notifyDataSetChanged();
        String urlString ="https://api.themoviedb.org/3/movie/popular?page="+urlStringId+"&api_key=ea2dcee690e0af8bb04f37aa35b75075";
        HttpRequestService.startActionRequestHttp(getActivity().getApplicationContext(), urlString);

        return  view;
    }

    private List<Movie> parseTopStoriesResponse(String jsonString)
    {
        movies = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonMovies = jsonObject.getJSONArray("results");
            for (int i = 0; i<jsonMovies.length(); ++i)
            {
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);


                String vote_count = jsonMovie.getString("vote_count");
                String id = jsonMovie.getString("id");
                String video = jsonMovie.getString("video");
                String title = jsonMovie.getString("title");
                String vote_average = jsonMovie.getString("vote_average");
                String popularity = jsonMovie.getString("popularity");
                String poster_path = "https://image.tmdb.org/t/p/w500/"+jsonMovie.getString("poster_path");
                String original_language = jsonMovie.getString("original_language");
                String original_title = jsonMovie.getString("original_title");
                String genre_ids = jsonMovie.getString("genre_ids");
                String backdrop_path = jsonMovie.getString("backdrop_path");
                String adult = jsonMovie.getString("adult");
                String overview = jsonMovie.getString("overview");
                String release_date = jsonMovie.getString("release_date");
                Movie newMovie = new Movie(vote_count,id,video, vote_average,title,popularity,poster_path,original_language,original_title,
                        genre_ids,backdrop_path,adult,overview,release_date);

                movies.add(newMovie);
            }

        } catch (JSONException e) {
            Log.e(TAG,"can't parse json string correctly");
            e.printStackTrace();
        }

        return movies;
    }

    private class HttpReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {

            // if the request succeeded
            if (intent.getAction().equals("httpRequestComplete")) {

                // get the JSON response from the intent
                String response = intent.getStringExtra("responseString");

                // parse the JSON into Movies
                List<Movie> Movies = parseTopStoriesResponse(response);

                // update the RecyclerView
                SWMoviesAdapter.setMovies(Movies);


            }

            else if (intent.getAction().equals("httpRequestFailed")) {
//                List<Movie> Movies = SavedMoviesManager.getInstance().getAllMovies();
                List<Movie> Movies = new ArrayList<>();
                SWMoviesAdapter.setMovies(Movies);
            }
        }
    }
}
