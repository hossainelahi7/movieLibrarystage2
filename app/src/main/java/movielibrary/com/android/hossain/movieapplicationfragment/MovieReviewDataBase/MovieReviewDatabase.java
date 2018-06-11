package movielibrary.com.android.hossain.movieapplicationfragment.MovieReviewDataBase;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;

@Database(entities = {ReviewData.class}, version = 1)
public abstract class MovieReviewDatabase extends RoomDatabase{

    abstract MovieReviewDao movieReviewDao();

    private static MovieReviewDatabase INSTANCE;

    public static MovieReviewDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MovieReviewDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieReviewDatabase.class,
                            BuildConfig.MOVIE_REVIEW_DB_NAME)
                            .addCallback(sReviewDatabaseCallback)
                            .build();
//                    new MovieInfoDatabase.PopulateDbAsync(INSTANCE).execute();
                }
            }
        }
        return INSTANCE;
    }

    private static MovieReviewDatabase.Callback sReviewDatabaseCallback = new MovieReviewDatabase.Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
//            new PopulateDbAsync(INSTANCE).execute();
        }
    };



//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final MovieReviewDao mDao;
//
//        PopulateDbAsync(MovieInfoDatabase db) {
//            mDao = db.movieReviewDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            List<ReviewData> reviewData = DataSync.syncReviewData();
//            mDao.insertAll(movieInfos);
//            movieInfos = DataSync.syncTopMovies();
//            mDao.insertAll(movieInfos);
//            return null;
//        }
//    }

}
