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

    public List<MovieInfo> getPopularMovieInfoList() {
        List<MovieInfo> movieInfos = mMovieInfoDao.getPopulerMovies();
        if(movieInfos == null || movieInfos.size()<=0){
            movieInfos = DataSync.syncPopularMovies();
                for (int i = 0; i < movieInfos.size(); i++){
                    MovieInfo temp = mMovieInfoDao.getMovieInfoDetails(movieInfos.get(i).movie_id);
                    if(temp!=null){
                        temp.populer_movie = 1;
                        mMovieInfoDao.insert(temp);
                    }else {
                        mMovieInfoDao.insert(movieInfos.get(i));
                    }
                }
        }
        return movieInfos;
    }

    public List<MovieInfo> getTopRatedMovieInfoList() {
        List<MovieInfo> movieInfos = mMovieInfoDao.getTopMovies();
        if(movieInfos == null || movieInfos.size()<=0){
            movieInfos = DataSync.syncTopMovies();
            for (int i = 0; i < movieInfos.size(); i++){
                MovieInfo temp = mMovieInfoDao.getMovieInfoDetails(movieInfos.get(i).movie_id);
                if(temp!=null){
                    temp.top_movie = 1;
                    mMovieInfoDao.insert(temp);
                }else {
                    mMovieInfoDao.insert(movieInfos.get(i));
                }
            }

        }
        return movieInfos;
    }

    public List<MovieInfo> getUserChoiceMovieInfo (){
        return mMovieInfoDao.getUserChoiceMovies();
    }

    public void insert(MovieInfo movieInfo){
        mMovieInfoDao.insert(movieInfo);
    }

    public MutableLiveData<MovieInfo> getMovieDetailsInfo(int id){
        MutableLiveData<MovieInfo> data = new MutableLiveData<>();
        data.postValue(mMovieInfoDao.getMovieInfoDetails(id));
        return data;
    }

    public MovieInfo getMovieInfo(int id){
        return mMovieInfoDao.getMovieInfoDetails(id);
    }

    public List<ReviewData> getReviewofMovie(int movieID) {
        List<ReviewData> reviewDataList = mMovieReviewDao.getReviwForMovie(movieID);
        if(reviewDataList == null || reviewDataList.size()<=0){
            reviewDataList = DataSync.syncReviewData(movieID);
            if(reviewDataList != null)
                mMovieReviewDao.insertAll(reviewDataList);
        }
        return reviewDataList;
    }

    public void insert(ReviewData... movieReview){
        mMovieReviewDao.insert(movieReview);
    }

    public List<VideoData> getVideoofMovie(int movieID) {
        List<VideoData> videoDataList = mMovieVideoDao.getVideoForMovie(movieID);
        if(videoDataList == null || videoDataList.size()<=0){
            videoDataList = DataSync.syncVideoData(movieID);
            mMovieVideoDao.insertAll(videoDataList);
        }
        return videoDataList;
    }

    public void insert(VideoData... movieVideo){
        mMovieVideoDao.insert(movieVideo);
    }

}
