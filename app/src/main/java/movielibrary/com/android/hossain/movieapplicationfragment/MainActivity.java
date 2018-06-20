package movielibrary.com.android.hossain.movieapplicationfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

import movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase.MovieDBRepository;
import movielibrary.com.android.hossain.movieapplicationfragment.MovieReviewDataBase.ReviewDBRepository;
import movielibrary.com.android.hossain.movieapplicationfragment.MovieVideoDataBase.VideoDBRepository;

public class MainActivity extends AppCompatActivity {
    public static MovieDBRepository movieDB;
    public static VideoDBRepository videoDb;
    public static ReviewDBRepository reviewDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        movieDB = new MovieDBRepository(this.getApplication());
        videoDb = new VideoDBRepository(this.getApplication());
        reviewDB = new ReviewDBRepository(this.getApplication());
    }


//    private class LoadData extends AsyncTask<Void, Void, Void>{
//        private MovieDBRepository repo;
//        private LifecycleOwner owner;
//
//        public LoadData(MovieDBRepository repository, LifecycleOwner own){
//            repo = repository;
//            owner = own;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            MutableLiveData<List<MovieInfo>> movieInfo = repo.getPopularMovieInfoList();
//            movieInfo.observe(owner, new Observer<List<MovieInfo>>() {
//                @Override
//                public void onChanged(@Nullable List<MovieInfo> movieInfos) {
//                    Log.d("GetData", String.valueOf(movieInfos.size()));
//                }
//            });
//            return null;
//        }
//    }

//    public void prioritySelected(View view) {
//        int id;
//        switch (view.getId()){
//            case R.id.top_movie:
//                id = Translator.POPUPAR_MOVIE;
//                break;
//            case R.id.top_tv:
//                id = Translator.USER_LISTED;
//                break;
//            case R.id.top_rated_movie:
//                id = Translator.TOP_RATED_MOVIE;
//                break;
//            default:
//                id = Translator.POPUPAR_MOVIE;
//                break;
//        }
////        Log.d("Clicked", "Top Item Clicked, id="+id);
////        mLoadData = new LoadData();
////        mLoadData.execute(id);
//    }
}
