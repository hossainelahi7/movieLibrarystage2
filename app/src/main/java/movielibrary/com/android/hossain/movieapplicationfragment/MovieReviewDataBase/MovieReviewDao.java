package movielibrary.com.android.hossain.movieapplicationfragment.MovieReviewDataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;

@Dao
public interface MovieReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReviewData... reviewData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReviewData> reviewData );

    @Query("SELECT * from "+ BuildConfig.MOVIE_REVIEW_TABLE_NAME+" ORDER BY review_id ASC")
    List<ReviewData> getAllReview();

    @Query("SELECT * from "+BuildConfig.MOVIE_REVIEW_TABLE_NAME+" WHERE movie_id = :movieId ORDER BY review_id ASC")
    List<ReviewData> getReviwForMovie(int movieId);

    @Delete
    void deleteEntry(ReviewData... reviewData);

    @Query("DELETE FROM "+ BuildConfig.MOVIE_REVIEW_TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM "+ BuildConfig.MOVIE_REVIEW_TABLE_NAME +" WHERE movie_id = :movie_id")
    void deleteAllForMovie(int movie_id);

}
