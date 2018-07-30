package eu.epfc.pocketmovie.model;


import java.io.Serializable;



public class Movie implements Serializable{

    private String vote_count;
    private String id;
    private String video;
    private String vote_average;
    private String title;
    private String popularity;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String genre_ids;
    private String backdrop_path;
    private String adult;
    private String overview;
    private String release_date;




    public Movie(String vote_count, String id, String video,String vote_average,String title,String popularity, String poster_path, String original_language,
                 String original_title, String genre_ids, String backdrop_path, String adult, String overview, String release_date) {
        this.vote_count = vote_count;
        this.id = id;
        this.video = video;
        this.title = title;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre_ids = genre_ids;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;

    }

    public String getTitle() {
        return title;
    }

    public String getVote_average() {
        return   vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getVote_count() {
        return vote_count;
    }

    public String getId() {
        return id;
    }

    public String getVideo() {
        return video;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getGenre_ids() {
        return genre_ids;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }
}
