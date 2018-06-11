package movielibrary.com.android.hossain.movieapplicationfragment.MovieVideoDataBase;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;

@Database(entities = {VideoData.class}, version = 1)
public abstract class MovieVideoDatabase extends RoomDatabase{

    abstract MovieVideoDao movieVideoDao();

    private static MovieVideoDatabase INSTANCE;

    public static MovieVideoDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieVideoDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieVideoDatabase.class,
                            BuildConfig.MOVIE_VIDEO_DB_NAME)
                            .addCallback(sVideoDatabaseCallback)
                            .build();
//                    new MovieInfoDatabase.PopulateDbAsync(INSTANCE).execute();
                }
            }
        }
        return INSTANCE;
    }

    private static MovieVideoDatabase.Callback sVideoDatabaseCallback = new MovieVideoDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
//            new PopulateDbAsync(INSTANCE).execute();
        }
    };
}
