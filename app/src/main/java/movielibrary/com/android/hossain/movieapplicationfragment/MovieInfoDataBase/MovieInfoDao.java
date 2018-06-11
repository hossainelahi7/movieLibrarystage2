package movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;

@Dao
public interface MovieInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieInfo... movieInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieInfo> movieInfos );

    @Query("SELECT * from "+BuildConfig.MOVIE_TABLE_NAME+" ORDER BY avg_vote ASC")
    List<MovieInfo> getAllMovies();

    @Query("SELECT * from "+BuildConfig.MOVIE_TABLE_NAME+" WHERE populer_movie = 1 ORDER BY avg_vote ASC")
    List<MovieInfo> getPopulerMovies();

    @Query("SELECT * from "+BuildConfig.MOVIE_TABLE_NAME+" WHERE top_movie = 1 ORDER BY avg_vote ASC")
    List<MovieInfo> getTopMovies();

    @Query("SELECT * from "+BuildConfig.MOVIE_TABLE_NAME+" WHERE user_choice = 1 ORDER BY avg_vote ASC")
    List<MovieInfo> getUserChoiceMovies();

    @Delete
    void deleteEntry(MovieInfo movieInfo);

    @Query("DELETE FROM "+ BuildConfig.MOVIE_TABLE_NAME)
    void deleteAll();

    @Query("SELECT * from "+BuildConfig.MOVIE_TABLE_NAME+" WHERE movie_id = :id ORDER BY avg_vote ASC")
    MovieInfo getMovieInfoDetails(int id);

}
