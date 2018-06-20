package movielibrary.com.android.hossain.movieapplicationfragment.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import movielibrary.com.android.hossain.movieapplicationfragment.BuildConfig;

@Entity(tableName = BuildConfig.MOVIE_VIDEO_TABLE_NAME)
public class VideoData {

    @PrimaryKey
    @NonNull
    public String video_id;
    @NonNull
    public String ios_639_1;
    @NonNull
    public String iso_3166_1;
    @NonNull
    public String video_key;
    @NonNull
    public String video_name;
    @NonNull
    public String site;
    @NonNull
    public int video_size;
    @NonNull
    public String video_type;
    @NonNull
    public int movie_id;


    public VideoData(    @NonNull String video_id,
                         @NonNull String ios_639_1,
                         @NonNull String iso_3166_1,
                         @NonNull String video_key,
                         @NonNull String video_name,
                         @NonNull String site,
                         @NonNull int video_size,
                         @NonNull String video_type,
                         @NonNull int movie_id){
        this.video_id = video_id;
        this.ios_639_1 = ios_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.video_key = video_key;
        this.video_name = video_name;
        this.site = site;
        this.video_size = video_size;
        this.video_type = video_type;
        this.movie_id = movie_id;
    }
}
