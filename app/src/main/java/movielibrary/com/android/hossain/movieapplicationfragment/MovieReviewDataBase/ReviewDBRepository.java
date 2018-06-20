package movielibrary.com.android.hossain.movieapplicationfragment.MovieReviewDataBase;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.DataUtil.DataSync;

public class ReviewDBRepository{

    private MovieReviewDao mMovieReviewDao;

    public ReviewDBRepository(Application application){
        MovieReviewDatabase db = MovieReviewDatabase.getDatabase(application);
        mMovieReviewDao = db.movieReviewDao();
    }

    public MutableLiveData<List<ReviewData>> getReviewofMovie(int movieID) {
        List<ReviewData> reviewDataList = mMovieReviewDao.getReviwForMovie(movieID);
        if(reviewDataList == null || reviewDataList.size()<=0){
            reviewDataList = DataSync.syncReviewData(movieID);
            mMovieReviewDao.insertAll(reviewDataList);
        }
        MutableLiveData<List<ReviewData>> data = new MutableLiveData<>();
        data.postValue(reviewDataList);
        return data;
    }

    public void insert(ReviewData... movieReview){
        mMovieReviewDao.insert(movieReview);
    }


}
