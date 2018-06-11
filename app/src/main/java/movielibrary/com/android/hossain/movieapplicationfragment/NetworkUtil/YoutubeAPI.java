package movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class YoutubeAPI {
    public static final String YOUTUBE_URL_Sample = "https://www.youtube.com/watch?v=6ZfuNTqbHE8";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch";



    public static URL getYoutubeUrl(String videoID){
        Uri queryUri = Uri.parse(YOUTUBE_URL).buildUpon()
                .appendQueryParameter("v", videoID)
                .build();
        try {
            URL url = new URL(queryUri.toString());
            Log.d("YoutubeAPI", url.toString());
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
