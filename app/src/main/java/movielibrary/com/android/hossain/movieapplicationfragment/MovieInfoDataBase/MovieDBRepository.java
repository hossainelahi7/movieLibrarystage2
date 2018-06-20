package movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;
import movielibrary.com.android.hossain.movieapplicationfragment.DataUtil.DataSync;

public class MovieDBRepository {
    private MovieInfoDao mMovieInfoDao;
    private MovieReviewDao mMovieReviewDao;
    private MovieVideoDao mMovieVideoDao;


    public MovieDBRepository(Application application){
        MovieInfoDatabase db = MovieInfoDatabase.getDatabase(application);
        mMovieInfoDao = db.movieInfoDao();
        mMovieReviewDao = db.movieReviewDao();
        mMovieVideoDao = db.movieVideoDao();
    }

    public MutableLiveData<List<MovieInfo>> getPopularMovieInfoList() {
        List<MovieInfo> movieInfos = mMovieInfoDao.getPopulerMovies();
        if(movieInfos == null || movieInfos.size()<=0){
            movieInfos = DataSync.syncPopularMovies();
            mMovieInfoDao.insertAll(movieInfos);
        }
        MutableLiveData<List<MovieInfo>> data = new MutableLiveData<>();
        data.postValue(movieInfos);
        return data;
    }

    public MutableLiveData<List<MovieInfo>> getTopRatedMovieInfoList() {
        List<MovieInfo> movieInfos = mMovieInfoDao.getTopMovies();
        if(movieInfos == null || movieInfos.size()<=0){
            movieInfos = DataSync.syncTopMovies();
            mMovieInfoDao.insertAll(movieInfos);
        }
        MutableLiveData<List<MovieInfo>> data = new MutableLiveData<>();
        data.postValue(movieInfos);
        return data;
    }

    public MutableLiveData<List<MovieInfo>> getUserChoiceMovieInfo (){
        MutableLiveData<List<MovieInfo>> data = new MutableLiveData<>();
        data.postValue(mMovieInfoDao.getUserChoiceMovies());
        return data;
    }

    public void insert(MovieInfo movieInfo){
        mMovieInfoDao.insert(movieInfo);
    }

    public MovieInfo getMovieDetailsInfo(int id){
        return mMovieInfoDao.getMovieInfoDetails(id);
    }


    public MutableLiveData<List<ReviewData>> getReviewofMovie(int movieID) {
        List<ReviewData> reviewDataList = mMovieReviewDao.getReviwForMovie(movieID);
        if(reviewDataList == null || reviewDataList.size()<=0){
            reviewDataList = DataSync.syncReviewData(movieID);
            if(reviewDataList != null)
                mMovieReviewDao.insertAll(reviewDataList);
        }
        MutableLiveData<List<ReviewData>> data = new MutableLiveData<>();
        data.postValue(reviewDataList);
        return data;
    }

    public void insert(ReviewData... movieReview){
        mMovieReviewDao.insert(movieReview);
    }

    public MutableLiveData<List<VideoData>> getVideoofMovie(int movieID) {
        List<VideoData> reviewDataList = mMovieVideoDao.getVideoForMovie(movieID);
        if(reviewDataList == null || reviewDataList.size()<=0){
            reviewDataList = DataSync.syncVideoData(movieID);
            mMovieVideoDao.insertAll(reviewDataList);
        }
        MutableLiveData<List<VideoData>> videoData = new MutableLiveData<>();
        videoData.postValue(reviewDataList);
        return videoData;
    }

    public void insert(VideoData... movieVideo){
        mMovieVideoDao.insert(movieVideo);
    }

}
