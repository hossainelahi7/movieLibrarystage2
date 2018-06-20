package movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;

@Dao
public interface MovieVideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VideoData... videoData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<VideoData> videoData );

    @Query("SELECT * from "+ BuildConfig.MOVIE_VIDEO_TABLE_NAME+" ORDER BY video_id ASC")
    List<VideoData> getAllVideo();

    @Query("SELECT * from "+BuildConfig.MOVIE_VIDEO_TABLE_NAME+" WHERE movie_id = :movieId ORDER BY movie_id ASC")
    List<VideoData> getVideoForMovie(int movieId);

    @Delete
    void deleteEntry(VideoData... videoData);

    @Query("DELETE FROM "+ BuildConfig.MOVIE_VIDEO_TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM "+ BuildConfig.MOVIE_VIDEO_TABLE_NAME+ " WHERE movie_id = :movie_id")
    void deleteAllForMovie(int movie_id);

}
