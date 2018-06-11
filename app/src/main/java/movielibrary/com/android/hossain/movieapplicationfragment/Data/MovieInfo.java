package movielibrary.com.android.hossain.movieapplicationfragment.Data;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;

@Entity(tableName = BuildConfig.MOVIE_TABLE_NAME)
public class MovieInfo {

    @PrimaryKey
    @NonNull
    public int movie_id;
    @NonNull
    public int vote_count;
    public int video; //boolean
    @NonNull
    public Double avg_vote;
    @NonNull
    public String movie_title;
    @NonNull
    public Double popularity;
    @NonNull
    public String posterpath;
    @NonNull
    public String original_language;
    @NonNull
    public String original_title;
    @NonNull
    public String backdrop_path;
    public int adult; //boolean
    @NonNull
    public String overview;
    @NonNull
    public String release_data;
    public int top_movie; //boolean
    public int populer_movie; //boolean
    public int user_choice; //boolean

    public MovieInfo(@NonNull int movie_id,
                     @NonNull int vote_count,
                     @NonNull int video,
                     @NonNull Double avg_vote,
                     @NonNull String movie_title,
                     @NonNull Double popularity,
                     @NonNull String posterpath,
                     @NonNull String original_language,
                     @NonNull String original_title,
                     @NonNull String backdrop_path,
                     @NonNull int adult,
                     @NonNull String overview,
                     @NonNull String release_data,
                     @NonNull int top_movie,
                     @NonNull int populer_movie,
                     @NonNull int user_choice){
        this.movie_id = movie_id;
        this.vote_count = vote_count;
        this.video = video;
        this.avg_vote = avg_vote;
        this.movie_title = movie_title;
        this.popularity = popularity;
        this.posterpath = posterpath;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_data = release_data;
        this.top_movie = top_movie;
        this.populer_movie = populer_movie ;
        this.user_choice = user_choice;
    }

}
