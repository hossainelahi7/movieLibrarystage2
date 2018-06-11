package movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;

public class MovieDBRepository {
    private MovieInfoDao mMovieInfoDao;

    public MovieDBRepository(Context context){
        MovieInfoDatabase db = MovieInfoDatabase.getDatabase(context);
        mMovieInfoDao = db.movieInfoDao();
    }

    public MutableLiveData<List<MovieInfo>> getPopularMovieInfoList() {
        MutableLiveData<List<MovieInfo>> data = new MutableLiveData<>();
        data.postValue(mMovieInfoDao.getAllMovies());
        return data;
    }

    public MutableLiveData<List<MovieInfo>> getTopRatedMovieInfoList() {
        MutableLiveData<List<MovieInfo>> data = new MutableLiveData<>();
        data.postValue(mMovieInfoDao.getTopMovies());
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
