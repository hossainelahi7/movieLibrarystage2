package movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;

/**
 * Created by hossain on 4/25/18.
 */

public class MovieDBAPI {
    private static final String API_KEY = BuildConfig.MOVIE_API_KEY;
    private static final String API_LINK = "https://api.themoviedb.org/3";
    private static final String POPULAR_MOVIE_STRING = API_LINK+"/movie/popular";
    private static final String POPULAR_TV_STRING = API_LINK+"/tv/popular";
    private static final String TOP_RATED_MOVIE_STRING = API_LINK+"/movie/top_rated";
    private static final String API_IMAGE_URL = "https://image.tmdb.org/t/p/w500";


    public static String getPopularMovieResponse(){
        URL popularURL = buildURL(POPULAR_MOVIE_STRING);
        String response = getURLResponse(popularURL);
        return response;
    }

    public static String getApiImageUrl(String imageLink){
        return API_IMAGE_URL+imageLink;
    }

    public static String getMovieTrailerURL(int movieId){
        return API_LINK+"/movie/"+movieId+"/videos";
    }
    public static String getMovieReviewURL(int movieId){
        return API_LINK+"/movie/"+movieId+"/reviews";
    }
    public static String getTVTrailerURL(int movieId){
        return API_LINK+"/tv/"+movieId+"/videos";
    }
    public static String getTVReviewURL(int movieId){
        return API_LINK+"/tv/"+movieId+"/reviews";
    }

    public static String getPopularTVResponse(){
        return getURLResponse(buildURL(POPULAR_TV_STRING));
    }

    public static String getTopRatedMovieResponse(){
        return getURLResponse(buildURL(TOP_RATED_MOVIE_STRING));
    }

    public static String getMovieTrailerResponse(int movieId){
        URL movieTrailerURL = buildURL(getMovieTrailerURL(movieId));
        return getURLResponse(movieTrailerURL);
    }

    public static String getTVTrailerResponse(int TVId){
        URL TVTrailerURL = buildURL(getTVTrailerURL(TVId));
        return getURLResponse(TVTrailerURL);
    }

    public static String getMovieReviewResponse(int movieId){
        URL movieTrailerURL = buildURL(getMovieReviewURL(movieId));
        return getURLResponse(movieTrailerURL);
    }

    public static String getTVReviewResponse(int TVId){
        URL TVTrailerURL = buildURL(getTVReviewURL(TVId));
        return getURLResponse(TVTrailerURL);
    }


    private static String getURLResponse(URL url){
        String response = "";
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            try {
                connection.connect();
                int status = connection.getResponseCode();
                switch (status){
                    case 200:
                    case 201:
                        InputStream in = connection.getInputStream();
                        Scanner scanner = new Scanner(in);
                        while (scanner.hasNext()) {
                            response = response + scanner.next()+" ";
                        }
                        scanner.close();
                        break;
                    default:
                        response = "Unexpected network Error";
                        break;
                }
            } finally {
                connection.disconnect();
            }
        }
        catch (MalformedURLException e) {}
        catch (IOException e) {}
        Log.d("Response", response);
        return response;
    }

    private static URL buildURL(String link){
        Uri queryUri = Uri.parse(link).buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .build();
        try {
            URL url = new URL(queryUri.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
