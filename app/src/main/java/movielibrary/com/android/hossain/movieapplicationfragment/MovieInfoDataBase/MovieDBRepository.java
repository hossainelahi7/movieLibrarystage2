package movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.DataUtil.DataSync;

public class MovieDBRepository {
    private MovieInfoDao mMovieInfoDao;

    public MovieDBRepository(Application application){
        MovieInfoDatabase db = MovieInfoDatabase.getDatabase(application);
        mMovieInfoDao = db.movieInfoDao();
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

}
