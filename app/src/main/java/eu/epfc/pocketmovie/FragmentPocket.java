package eu.epfc.pocketmovie;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import eu.epfc.pocketmovie.model.HttpRequestService;
import eu.epfc.pocketmovie.model.Movie;
import eu.epfc.pocketmovie.model.SavedMoviesManager;

public  class FragmentPocket extends Fragment implements SWMoviesPocketAdapter.ListItemClickListener {

    private List<Movie> movies;
    private List<String> moviesId;
    private static String TAG = "MainActivity";

    private HttpRequestService httpRequestService = new HttpRequestService();
    private SWMoviesPocketAdapter SWMoviesPocketAdapter;

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Movie selectedMovie = movies.get(clickedItemIndex);

        Intent detailIntent = new Intent(getActivity(),DetailActivity.class);
        detailIntent.putExtra("movieObject", selectedMovie);
        startActivity(detailIntent);
    }
    public FragmentPocket() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pocket_fragment, container,
                false);
        SavedMoviesManager.getInstance().initWithContext(getActivity().getApplicationContext());
        movies  = SavedMoviesManager.getInstance().getAllMovies();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_movies_pocket);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        SWMoviesPocketAdapter = new SWMoviesPocketAdapter(this);
        recyclerView.setAdapter(SWMoviesPocketAdapter);
        SWMoviesPocketAdapter.setMovies(movies);

        return  view;
    }
}
