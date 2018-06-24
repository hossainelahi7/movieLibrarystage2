package movielibrary.com.android.hossain.movieapplicationfragment.Fragment;


//import android.arch.lifecycle.LifecycleOwner;
//import android.arch.lifecycle.MutableLiveData;
//import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.DataUtil.Translator;
import movielibrary.com.android.hossain.movieapplicationfragment.MainActivity;
import movielibrary.com.android.hossain.movieapplicationfragment.R;
import movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView.MovieImageRecylerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListViewFragment extends Fragment{

    private Context mContext;
    private MovieImageRecylerViewAdapter mMovieAdapter;
    private RecyclerView mMovieView;
    private LoadData loadData;
    private Integer movieType = Translator.POPUPAR_MOVIE;
//    private Integer movieTypeLive = new MutableLiveData();
    private Button populerMovie;
    private Button topRatedMovie;
    private Button userListMovie;
//    private LifecycleOwner lifecycleOwner;
    private List<MovieInfo> movieInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        mContext = getContext();
        super.onActivityCreated(savedInstanceState);
//        movieTypeLive.setValue(movieType);
        mMovieView = getActivity().findViewById(R.id.movie_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mMovieView.setLayoutManager(layoutManager);
        mMovieAdapter = new MovieImageRecylerViewAdapter();
        mMovieView.setAdapter(mMovieAdapter);
        populerMovie = getActivity().findViewById(R.id.top_movie);
        topRatedMovie = getActivity().findViewById(R.id.top_rated_movie);
        userListMovie = getActivity().findViewById(R.id.user_listing);
        populerMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieType = Translator.POPUPAR_MOVIE;
                reloadData(movieType);
            }
        });
        topRatedMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieType = Translator.TOP_RATED_MOVIE;
                reloadData(movieType);
            }
        });
        userListMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieType = Translator.USER_LISTED;
                reloadData(movieType);
            }
        });
    }

    private void reloadData(int movieType){
//        if(loadData.getStatus() != AsyncTask.Status.RUNNING){
            loadData = new LoadData();
            loadData.execute(movieType);
//        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStart() {
        super.onStart();
//        loadData = new LoadData();
//        loadData.execute(movieType);
//        reloadData(movieType);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData(movieType);
    }



    private class LoadData extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(mContext, "Data Loading .....", Toast.LENGTH_LONG);
        }

        @Override
        protected Boolean doInBackground(Integer... ints) {
//            if(ints.length<=0)
//                return false;
            switch (ints[0]){
                case Translator.POPUPAR_MOVIE:
                    movieInfo = MainActivity.movieDB.getPopularMovieInfoList();
                    break;
                case Translator.TOP_RATED_MOVIE:
                    movieInfo = MainActivity.movieDB.getTopRatedMovieInfoList();
                    break;
                case Translator.USER_LISTED:
                    movieInfo = MainActivity.movieDB.getUserChoiceMovieInfo();
                    break;
                default:
                    movieInfo = MainActivity.movieDB.getPopularMovieInfoList();
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            mMovieAdapter.setMoviedata(movieInfo);
            if(movieInfo.size() == 0)
                Toast.makeText(mContext, "No entry found for this movie type selection", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
//        movieType = movieTypeLive.getValue();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    private LifecycleOwner getLifecycleOwner(Context context) {
//        while (!(context instanceof LifecycleOwner)) {
//            context = ((ContextWrapper) context).getBaseContext();
//        }
//        return (LifecycleOwner) context;
//    }

}
