package movielibrary.com.android.hossain.movieapplicationfragment.DataUtil;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;

public class Translator {

    public static final int POPUPAR_MOVIE = 1;
    public static final int TOP_RATED_MOVIE = 2;
    public static final int USER_LISTED = 3;

        private class MovieKeys{
            public static final String VOTE_COUNT = "vote_count";
            public static final String ID = "id";
            public static final String VIDEO = "video";
            public static final String AVG_VOTE = "vote_average";
            public static final String TITLE = "title";
            public static final String POPULARITY = "popularity";
            public static final String POSTER_PATH = "poster_path";
            public static final String ORIGINAL_LANGUAGE = "original_language";
            public static final String ORIGINAL_TITLE = "original_titile";
            public static final String BACKDROP_PATH = "backdrop_path";
            public static final String ADULT = "adult";
            public static final String OVERVIEW = "overview";
            public static final String RELEASE_DATE = "release_date";
        }


    private class ViedeoKeys{
        public static final String ID = "id";
        public static final String ISO_639_1 = "iso_639_1";
        public static final String ISO_3166_1 = "iso_3166_1";
        public static final String KEY = "key";
        public static final String NAME = "name";
        public static final String SITE = "site";
        public static final String SIZE = "size";
        public static final String TYPE = "type";
    }

    private class MovieReviewKeys {
        public static final String AUTHOR = "author";
        public static final String CONTENT = "content";
        public static final String ID = "id";
        public static final String URL = "url";
    }


    public static MovieInfo convertToMovieInfo(@NonNull JSONObject jsonObject, @NonNull int dataType){
        int id = JsonUtil.getInt(jsonObject, MovieKeys.ID);
        int voteCount = JsonUtil.getInt(jsonObject, MovieKeys.VOTE_COUNT);
        int video = (JsonUtil.getBoolean(jsonObject, MovieKeys.VIDEO))? 1: 0; //boolean
        Double avgVote = JsonUtil.getDouble(jsonObject, MovieKeys.AVG_VOTE);
        String title = JsonUtil.getString(jsonObject, MovieKeys.TITLE);
        Double popularity = JsonUtil.getDouble(jsonObject, MovieKeys.POPULARITY);
        String posterPath = JsonUtil.getString(jsonObject, MovieKeys.POSTER_PATH);
        String originalLanguage = JsonUtil.getString(jsonObject, MovieKeys.ORIGINAL_LANGUAGE);
        String originalTitle = JsonUtil.getString(jsonObject, MovieKeys.ORIGINAL_TITLE);
        String backdropPath = JsonUtil.getString(jsonObject, MovieKeys.BACKDROP_PATH);
        int adult = (JsonUtil.getBoolean(jsonObject, MovieKeys.ADULT))? 1 : 0; //boolean
        String overView  = JsonUtil.getString(jsonObject, MovieKeys.OVERVIEW);
        String releaseData = JsonUtil.getString(jsonObject, MovieKeys.RELEASE_DATE);
        int top_movie = (dataType == TOP_RATED_MOVIE)? 1: 0; //boolean
        int populer_movie = (dataType == POPUPAR_MOVIE)? 1: 0; //boolean
        int user_choice = (dataType == USER_LISTED)? 1: 0; //boolean
        return new MovieInfo(id, voteCount, video, avgVote, title, popularity, posterPath,
                originalLanguage, originalTitle, backdropPath, adult, overView, releaseData,
                top_movie, populer_movie, user_choice);
    }

    public static VideoData convertToVideoData(@NonNull JSONObject jsonObject, @NonNull int movieId){
        String video_id = JsonUtil.getString(jsonObject, ViedeoKeys.ID);
        String ios_639_1 = JsonUtil.getString(jsonObject, ViedeoKeys.ISO_639_1);
        String iso_3166_1 = JsonUtil.getString(jsonObject, ViedeoKeys.ISO_3166_1);
        String key = JsonUtil.getString(jsonObject, ViedeoKeys.KEY);
        String name = JsonUtil.getString(jsonObject, ViedeoKeys.NAME);
        String site = JsonUtil.getString(jsonObject, ViedeoKeys.SITE);
        int size = JsonUtil.getInt(jsonObject, ViedeoKeys.SIZE);
        String type = JsonUtil.getString(jsonObject, ViedeoKeys.TYPE);
        return new VideoData(video_id, ios_639_1, iso_3166_1, key, name, site, size, type, movieId);
    }

    public static ReviewData convertToReviewData(@NonNull JSONObject jsonObject, @NonNull int movieId){
        String Author = JsonUtil.getString(jsonObject, MovieReviewKeys.AUTHOR);
        String Content = JsonUtil.getString(jsonObject, MovieReviewKeys.CONTENT);
        String Id = JsonUtil.getString(jsonObject, MovieReviewKeys.ID);
        String Url = JsonUtil.getString(jsonObject, MovieReviewKeys.URL);
        return new ReviewData(Id, Author, Content, Url, movieId);
    }

    public static MovieInfo getSampleMovieInfo(){
            return new MovieInfo(0, 0, 0, 0.0, "", 0.0, "",
                    "", "", "", 0, " ", " ",0,
                    0, 0 );
    }

//    public static VideoData
}
