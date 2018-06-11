package movielibrary.com.android.hossain.movieapplicationfragment.MovieReviewDataBase;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.DataUtil.DataSync;

public class ReviewDBRepository{

    private MovieReviewDao mMovieReviewDao;

    public ReviewDBRepository(Context context){
        MovieReviewDatabase db = MovieReviewDatabase.getDatabase(context);
        mMovieReviewDao = db.movieReviewDao();
    }

    public MutableLiveData<List<ReviewData>> getReviewofMovie(int movieID) {
        List<ReviewData> reviewDataList = mMovieReviewDao.getReviwForMovie(movieID);
        if(!(reviewDataList.size()>0)){
            reviewDataList = DataSync.syncReviewData(movieID);
        }
        MutableLiveData<List<ReviewData>> data = new MutableLiveData<>();
        data.postValue(reviewDataList);
        return data;
    }

    public void insert(ReviewData... movieReview){
        mMovieReviewDao.insert(movieReview);
    }


}
