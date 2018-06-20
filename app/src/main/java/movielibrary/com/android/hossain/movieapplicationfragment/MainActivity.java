package movielibrary.com.android.hossain.movieapplicationfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

import movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase.MovieDBRepository;

public class MainActivity extends AppCompatActivity {
    public static MovieDBRepository movieDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        movieDB = new MovieDBRepository(this.getApplication());
    }

}
