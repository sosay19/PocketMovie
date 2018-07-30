package eu.epfc.pocketmovie.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;





public class SavedMoviesManager {

    private MovieDatabaseHelper MovieDatabaseHelper;

    private static final SavedMoviesManager ourInstance = new SavedMoviesManager();

    // Singleton instance
    public static SavedMoviesManager getInstance() {
        return ourInstance;
    }

    private SavedMoviesManager() {
    }

    public void initWithContext(Context context)
    {
        if (MovieDatabaseHelper == null) {
            MovieDatabaseHelper = new MovieDatabaseHelper(context);
        }
    }


    public void saveMovie(Movie movie)
    {

        MovieDatabaseHelper.addMovie(movie);

    }
//    public boolean getMovie(Movie movie)
//    {
//
////        return true;
//        return MovieDatabaseHelper.getMovie(movie.getId());
//
//    }
    public void deleteMovie(Movie movie)
    {

        MovieDatabaseHelper.clearMovie(movie.getId());


    }

    public void deleteAllMovies()
    {

        MovieDatabaseHelper.clearMovies();


    }
    public boolean getMovie(String id){


        boolean res = false;
        Cursor cursor = MovieDatabaseHelper.getReadableDatabase().query("MovieS",
                new String[]{"_id","VOTE_COUNT","ID","VIDEO","VOTE_AVERAGE","TITLE","POPULARITY","POSTER_PATH","ORIGINAL_LANGUAGE"
                        ,"ORIGINAL_TITLE","GENRE_IDS","BACKDROP_PATH","ADULT","OVERVIEW","RELEASE_DATE"},
                null,null,null,null,null);
        for (int i = 0 ; i < cursor.getCount(); i++)
        {

            if (cursor.moveToNext())
            {

                if( cursor.getString(2).equals(id))
                {
                    res = true;
                }
            }
        }

        cursor.close();

        return res;
    }

    /***
     * Read all the Movies stores in the database
     * @return a list of Movies object
     */
    public List<Movie> getAllMovies(){



        ArrayList<Movie> movies = new ArrayList<>();

        Cursor cursor = MovieDatabaseHelper.getReadableDatabase().query("MovieS",
                new String[]{"_id","VOTE_COUNT","ID","VIDEO","VOTE_AVERAGE","TITLE","POPULARITY","POSTER_PATH","ORIGINAL_LANGUAGE"
                        ,"ORIGINAL_TITLE","GENRE_IDS","BACKDROP_PATH","ADULT","OVERVIEW","RELEASE_DATE"},
                null,null,null,null,null);
        for (int i = 0 ; i < cursor.getCount(); i++)
        {

            if (cursor.moveToNext())
            {
                String vote_count = cursor.getString(1);
                String id = cursor.getString(2);
                String video = cursor.getString(3);
                String vote_average = cursor.getString(4);
                String title = cursor.getString(5);
                String popularity = cursor.getString(6);
                String poster_path = cursor.getString(7);
                String original_language = cursor.getString(8);
                String original_title = cursor.getString(9);
                String genre_ids = cursor.getString(10);
                String backdrop_path = cursor.getString(11);
                String adult = cursor.getString(12);
                String overview = cursor.getString(13);
                String release_date = cursor.getString(14);


                Movie Movie = new Movie(vote_count, id, video,  vote_average, title, popularity, poster_path,
                        original_language, original_title, genre_ids, backdrop_path,adult, overview,release_date);

                movies.add(Movie);
            }
        }

        cursor.close();

        return movies;
    }



    private class MovieDatabaseHelper extends SQLiteOpenHelper{

        private static final String DB_NAME = "PocketMovieDB";
        private static final int DB_VERSION = 1;

        MovieDatabaseHelper(Context context){
            super(context,DB_NAME,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String sqlInstruction = "CREATE TABLE MovieS (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "VOTE_COUNT TEXT,"+
                    "ID TEXT,"+
                    "VIDEO TEXT,"+
                    "VOTE_AVERAGE TEXT,"+
                    "TITLE TEXT,"+
                    "POPULARITY TEXT,"+
                    "POSTER_PATH TEXT,"+
                    "ORIGINAL_LANGUAGE TEXT,"+
                    "ORIGINAL_TITLE TEXT,"+
                    "GENRE_IDS TEXT,"+
                    "BACKDROP_PATH TEXT,"+
                    "ADULT TEXT,"+
                    "OVERVIEW TEXT,"+
                    "RELEASE_DATE TEXT)"

                    ;
            sqLiteDatabase.execSQL(sqlInstruction);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }


        public void addMovie(Movie movie)
        {
            ContentValues MovieValues = new ContentValues();
            MovieValues.put("VOTE_COUNT",movie.getVote_count());
            MovieValues.put("ID",movie.getId());
            MovieValues.put("VIDEO",movie.getVideo());
            MovieValues.put("VOTE_AVERAGE",movie.getVote_average());
            MovieValues.put("TITLE",movie.getTitle());
            MovieValues.put("POPULARITY",movie.getPopularity());
            MovieValues.put("POSTER_PATH",movie.getPoster_path());
            MovieValues.put("ORIGINAL_LANGUAGE",movie.getOriginal_language());
            MovieValues.put("ORIGINAL_TITLE",movie.getOriginal_title());
            MovieValues.put("GENRE_IDS",movie.getGenre_ids());
            MovieValues.put("BACKDROP_PATH",movie.getBackdrop_path());
            MovieValues.put("ADULT",movie.getAdult());
            MovieValues.put("OVERVIEW",movie.getOverview());
            MovieValues.put("RELEASE_DATE",movie.getRelease_date());
            getWritableDatabase().insert("MovieS",null,MovieValues);

        }


        public boolean clearMovie(String id) {


            return getWritableDatabase().delete("MovieS", "ID" + "=" + id, null) > 0;
        }


        /***
         * Delete all the raws in the table MovieS
         */
        public void clearMovies() {
            String clearDBQuery = "DELETE FROM MovieS";
            getWritableDatabase().execSQL(clearDBQuery);
        }
    }
}

