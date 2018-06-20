package movielibrary.com.android.hossain.movieapplicationfragment.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;

@Entity(tableName = BuildConfig.MOVIE_REVIEW_TABLE_NAME)
public class ReviewData {

    @PrimaryKey
    @NonNull
    public String review_id;
    @NonNull
    public String review_author;
    @NonNull
    public String review_content;
    @NonNull
    public String review_urls;
    @NonNull
    public int movie_id;

    public ReviewData(@NonNull String review_id,
                      @NonNull String review_author,
                      @NonNull String review_content,
                      @NonNull String review_urls,
                      @NonNull int movie_id){
        this.movie_id = movie_id;
        this.review_id = review_id;
        this.review_author = review_author;
        this.review_content = review_content;
        this.review_urls = review_urls;
    }
}
