package movielibrary.com.android.hossain.movieapplicationfragment.Fragment;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase.MovieDBRepository;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.DataUtil.Translator;
import movielibrary.com.android.hossain.movieapplicationfragment.R;
import movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView.MovieImageRecylerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListViewFragment extends Fragment {

//    public static final String FRAGMENT_NAME = "ListFragment";
//    public static final String FRAGMENT_STRING_TAG = "ListFrag";


    MovieDBRepository repo;
//    private Context mContext;
    private MovieImageRecylerViewAdapter mMovieAdapter;
    private RecyclerView mMovieView;
//    private LoadData mLoadData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMovieView = this.getActivity().findViewById(R.id.movie_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mMovieView.setLayoutManager(layoutManager);
        mMovieAdapter = new MovieImageRecylerViewAdapter();
        mMovieView.setAdapter(mMovieAdapter);
        repo = new MovieDBRepository(getContext());
        new LoadData(repo, this).execute();
    }

    public void prioritySelected(View view) {
        int id;
        switch (view.getId()){
            case R.id.top_movie:
                id = Translator.POPUPAR_MOVIE;
                break;
            case R.id.top_tv:
                id = Translator.USER_LISTED;
                break;
            case R.id.top_rated_movie:
                id = Translator.TOP_RATED_MOVIE;
                break;
            default:
                id = Translator.POPUPAR_MOVIE;
                break;
        }
//        Log.d("Clicked", "Top Item Clicked, id="+id);
//        mLoadData = new LoadData();
//        mLoadData.execute(id);
    }


    private class LoadData extends AsyncTask<Void, Void, Void> {
        private MovieDBRepository repo;
        private LifecycleOwner owner;

        public LoadData(MovieDBRepository repository, LifecycleOwner own){
            repo = repository;
            owner = own;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MutableLiveData<List<MovieInfo>> movieInfo = repo.getPopularMovieInfoList();
            movieInfo.observe(owner, new Observer<List<MovieInfo>>() {
                @Override
                public void onChanged(@Nullable List<MovieInfo> movieInfos) {
                    mMovieAdapter.setMoviedata(movieInfos);
                    Log.d("GetData", String.valueOf(movieInfos.size()));
                }
            });
            return null;
        }
    }

    private LifecycleOwner getLifecycleOwner(Context context) {
        while (!(context instanceof LifecycleOwner)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (LifecycleOwner) context;
    }

}
