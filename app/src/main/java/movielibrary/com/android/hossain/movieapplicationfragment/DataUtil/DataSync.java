package movielibrary.com.android.hossain.movieapplicationfragment.DataUtil;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;
import movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil.MovieDBAPI;

public class DataSync {
    public static List<MovieInfo> syncPopularMovies(){
        List<MovieInfo> movieInfos = new ArrayList<>();
        JSONArray responseArray = JsonUtil.convertReportToArray(MovieDBAPI.getPopularMovieResponse());
        if(responseArray != null){
            for( int i = 0 ; i < responseArray.length(); i ++){
                try {
                    movieInfos.add(Translator.convertToMovieInfo(responseArray.getJSONObject(i), Translator.POPUPAR_MOVIE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return movieInfos;
    }

    public static List<MovieInfo> syncTopMovies(){
        List<MovieInfo> movieInfos = new ArrayList<>();
        JSONArray responseArray = JsonUtil.convertReportToArray(MovieDBAPI.getPopularMovieResponse());
        if(responseArray != null){
            for( int i = 0 ; i < responseArray.length(); i ++){
                try {
                    movieInfos.add(Translator.convertToMovieInfo(responseArray.getJSONObject(i), Translator.TOP_RATED_MOVIE));
                } catch (JSONException e) {
                    Log.d(DataSync.class.getCanonicalName(), e.getMessage());
                }
            }
        }
        return movieInfos;
    }

    public static List<ReviewData> syncReviewData(int movieId){
        List<ReviewData> reviewDataList = new ArrayList<>();
        String movieReviewDataResponse = MovieDBAPI.getMovieReviewResponse(movieId);
        if(movieReviewDataResponse != null){
            JSONArray responseArray = JsonUtil.convertReportToArray(movieReviewDataResponse);
            for(int i= 0; i < responseArray.length(); i++){
                try {
                    reviewDataList.add(Translator.convertToReviewData(responseArray.getJSONObject(i), movieId));
                } catch (JSONException e) {
                    Log.d(DataSync.class.getCanonicalName(), e.getMessage());
                }
            }
        }
        return reviewDataList;
    }

    public static List<VideoData> syncVideoData(int movieId){
        List<VideoData> movieDataList = new ArrayList<>();
        String movieVideoDataResponse = MovieDBAPI.getMovieTrailerResponse(movieId);
        if(movieVideoDataResponse != null){
            JSONArray responseArray = JsonUtil.convertReportToArray(movieVideoDataResponse);
            for(int i= 0; i < responseArray.length(); i++){
                try {
                    movieDataList.add(Translator.convertToVideoData(responseArray.getJSONObject(i), movieId));
                } catch (JSONException e) {
                    Log.d(DataSync.class.getCanonicalName(), e.getMessage());
                }
            }
        }
        return movieDataList;
    }

}
