package movielibrary.com.android.hossain.movieapplicationfragment.Fragment;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.DataUtil.Translator;
import movielibrary.com.android.hossain.movieapplicationfragment.MainActivity;
import movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase.MovieDBRepository;
import movielibrary.com.android.hossain.movieapplicationfragment.R;
import movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView.MovieImageRecylerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListViewFragment extends Fragment{

    MovieDBRepository repo;
    private MovieImageRecylerViewAdapter mMovieAdapter;
    private RecyclerView mMovieView;
    private LoadData loadData;
    private int movieType = Translator.POPUPAR_MOVIE;


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
        super.onActivityCreated(savedInstanceState);
        mMovieView = getActivity().findViewById(R.id.movie_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mMovieView.setLayoutManager(layoutManager);
        mMovieAdapter = new MovieImageRecylerViewAdapter();
        mMovieView.setAdapter(mMovieAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStart() {
        super.onStart();
        repo = MainActivity.movieDB;
        loadData = new LoadData(repo, getLifecycleOwner(getContext()));
        loadData.execute(movieType);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

//    public void prioritySelected(View view) {
//
//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.top_movie:
//                movieType = Translator.POPUPAR_MOVIE;
//                break;
//            case R.id.top_tv:
//                movieType = Translator.USER_LISTED;
//                break;
//            case R.id.top_rated_movie:
//                movieType = Translator.TOP_RATED_MOVIE;
//                break;
//            default:
//                movieType = Translator.POPUPAR_MOVIE;
//                break;
//        }
//        if(loadData.getStatus() == AsyncTask.Status.FINISHED)
//            loadData.execute();
//    }


    private class LoadData extends AsyncTask<Integer, Void, Boolean> {
        private MovieDBRepository repo;
        private LifecycleOwner owner;
        MutableLiveData<List<MovieInfo>> movieInfo;

        public LoadData(MovieDBRepository repository, LifecycleOwner own){
            repo = repository;
            owner = own;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getContext(), "Data Loading .....", Toast.LENGTH_LONG);
        }

        @Override
        protected Boolean doInBackground(Integer... ints) {
            if(ints.length<=0)
                return false;
            switch (ints[0]){
                case Translator.POPUPAR_MOVIE:
                    movieInfo = repo.getPopularMovieInfoList();
                    break;
                case Translator.TOP_RATED_MOVIE:
                    movieInfo = repo.getPopularMovieInfoList();
                    break;
                case Translator.USER_LISTED:
                    movieInfo = repo.getUserChoiceMovieInfo();
                    break;
                default:
                    movieInfo = repo.getPopularMovieInfoList();
                    break;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            if(aVoid == false)
                return;
//            mMovieAdapter.setMoviedata(movieInfo);
            movieInfo.observe(owner, new Observer<List<MovieInfo>>() {
                @Override
                public void onChanged(@NonNull List<MovieInfo> movieInfos) {
                    if(movieInfos!=null && movieInfos.size() > 0)
                        mMovieAdapter.setMoviedata(movieInfos);
                    Log.d("GetData", String.valueOf(movieInfos.size()));
                }
            });
            this.onCancelled();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        loadData.cancel(true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private LifecycleOwner getLifecycleOwner(Context context) {
        while (!(context instanceof LifecycleOwner)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (LifecycleOwner) context;
    }

}
