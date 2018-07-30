package eu.epfc.pocketmovie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import eu.epfc.pocketmovie.model.HttpRequestService;
import eu.epfc.pocketmovie.model.Movie;
import eu.epfc.pocketmovie.model.SavedMoviesManager;

public class DetailActivity extends AppCompatActivity {

    private static String TAG = "DetailActivity";

    private Movie selectedMovie;
    private String genre ="";
    private String trailer ="";
    private  boolean checkTheBox=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the intent used to create this Activity
        Intent intent = getIntent();
        // extract a Movie object from it
         selectedMovie = (Movie) intent.getSerializableExtra("movieObject");


        /********************/
        DetailActivity.HttpReceiver httpReceiver = new DetailActivity.HttpReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("httpRequestComplete");
        intentFilter.addAction("httpRequestFailed");
        registerReceiver(httpReceiver,intentFilter);
        String urlString ="https://api.themoviedb.org/3/movie/"+selectedMovie.getId()+"?api_key=ea2dcee690e0af8bb04f37aa35b75075";
        String urlString1 ="https://api.themoviedb.org/3/movie/"+selectedMovie.getId()+"/videos?api_key=ea2dcee690e0af8bb04f37aa35b75075";
        HttpRequestService.startActionRequestHttp(getApplicationContext(), urlString);
        HttpRequestService.startActionRequestHttp(getApplicationContext(), urlString1);

        /********************/
        SavedMoviesManager.getInstance().initWithContext(getApplicationContext());

    }
    public void onCheckboxClicked(View view) {


        boolean checked = ((CheckBox) view).isChecked();

        if(checked)
        {
            SavedMoviesManager.getInstance().saveMovie(selectedMovie);
        }
        else
        {

            SavedMoviesManager.getInstance().deleteMovie(selectedMovie);

        }


    }
    public void onGoButtonClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube:"+ trailer));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.youtube");
        startActivity(intent);
    }
    private String parseGenreMovieResponse(String jsonString)
    {

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonMovies = jsonObject.getJSONArray("genres");

            for (int i = 0; i<jsonMovies.length(); ++i)
            {
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);


                String name = jsonMovie.getString("name");
                genre+=name+" ";
            }



        } catch (JSONException e) {
            Log.e(TAG,"can't parse json string correctly");
            e.printStackTrace();
        }
        TextView text_genre = findViewById(R.id.text_genre);
        text_genre.setText(genre);
        return genre;

    }

    private String parseTrailerMovieResponse(String jsonString)
    {


        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonMovies = jsonObject.getJSONArray("results");


            for (int i = 0; i<jsonMovies.length(); ++i)
            {
                JSONObject jsonMovie = jsonMovies.getJSONObject(i);
                 trailer = jsonMovie.getString("key");
            }



        } catch (JSONException e) {
            Log.e(TAG,"can't parse json string correctly");
            e.printStackTrace();
        }
        return trailer;


    }

    private class HttpReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            setContentView(R.layout.activity_detail);
            // if the request succeeded
            if (intent.getAction().equals("httpRequestComplete")) {

                // get the JSON response from the intent
                String response = intent.getStringExtra("responseString");

                // parse the JSON into Movies
                genre = parseGenreMovieResponse(response);
                trailer = parseTrailerMovieResponse(response);
                checkTheBox = SavedMoviesManager.getInstance().getMovie(selectedMovie.getId());
                CheckBox checkBox = findViewById(R.id.checkbox_pocket);
                checkBox.setChecked(checkTheBox);
                TextView movieTextView = findViewById(R.id.text_movie_title);
                movieTextView.setText(selectedMovie.getTitle());
                TextView text_release_date = findViewById(R.id.text_release_date);
                text_release_date.setText(selectedMovie.getRelease_date());

                TextView text_vote_average = findViewById(R.id.text_vote_average);
                text_vote_average.setText("Rating : " +selectedMovie.getVote_average());
                TextView text_overview = findViewById(R.id.text_overview);
                text_overview.setText(selectedMovie.getOverview());


                if (!selectedMovie.getBackdrop_path().isEmpty()) {
                    ImageView thumbnailImageView = findViewById(R.id.image_movie);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500/"+selectedMovie.getBackdrop_path()).into(thumbnailImageView);
                }

            }

            // else, if the request failed
            else if (intent.getAction().equals("httpRequestFailed")) {

            }

        }
    }

}