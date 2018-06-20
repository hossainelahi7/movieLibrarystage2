package movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;

@Database(entities = {MovieInfo.class, ReviewData.class, VideoData.class}, version = 1)
public abstract class MovieInfoDatabase extends RoomDatabase {
    abstract MovieInfoDao movieInfoDao();
    abstract MovieVideoDao movieVideoDao();
    abstract MovieReviewDao movieReviewDao();


    private static MovieInfoDatabase INSTANCE;

    public static MovieInfoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieInfoDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieInfoDatabase.class, BuildConfig.MOVIE_DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static MovieInfoDatabase.Callback sRoomDatabaseCallback = new MovieInfoDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
        }
    };

}
