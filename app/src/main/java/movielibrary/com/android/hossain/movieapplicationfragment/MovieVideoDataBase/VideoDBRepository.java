package movielibrary.com.android.hossain.movieapplicationfragment.MovieVideoDataBase;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;
import movielibrary.com.android.hossain.movieapplicationfragment.DataUtil.DataSync;

public class VideoDBRepository{

    private MovieVideoDao mMovieVideoDao;

    public VideoDBRepository(Application application){
        MovieVideoDatabase db = MovieVideoDatabase.getDatabase(application);
        mMovieVideoDao = db.movieVideoDao();
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
