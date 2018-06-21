package movielibrary.com.android.hossain.movieapplicationfragment.Fragment;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movielibrary.com.android.hossain.movieapplicationfragment.Data.MovieInfo;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.ReviewData;
import movielibrary.com.android.hossain.movieapplicationfragment.Data.VideoData;
import movielibrary.com.android.hossain.movieapplicationfragment.MainActivity;
import movielibrary.com.android.hossain.movieapplicationfragment.MovieInfoDataBase.MovieDBRepository;
import movielibrary.com.android.hossain.movieapplicationfragment.NetworkUtil.MovieDBAPI;
import movielibrary.com.android.hossain.movieapplicationfragment.R;
import movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView.MovieReviewRecylerAdapter;
import movielibrary.com.android.hossain.movieapplicationfragment.RecyclerView.MovieVideoRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsViewFragment extends Fragment {

    private Context mContext;
    private LifecycleOwner owner;
    private int MovieID;
    private TextView titleView;
    private ImageView imageView;
    private TextView descriptionView;
    private TextView popularityView;
    private TextView reviewView;
    private TextView releaseDateView;
    private RecyclerView mReviewRecyclerView;
    private RecyclerView mVideoRecyclerView;
    private MovieReviewRecylerAdapter reviewRecyler;
    private MovieVideoRecyclerAdapter videoRecycler;
    private Button saveButton;
    private MutableLiveData<MovieInfo> mMovieData = new MutableLiveData<>();
    private MutableLiveData<List<ReviewData>> mReviewData = new MutableLiveData<>();
    private MutableLiveData<List<VideoData>> mVideoData = new MutableLiveData<>();


    public DetailsViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("DETAILS", String.valueOf(getArguments().getInt("MOVIE_ID")));
        mContext = getContext();
        owner = this;
        MovieID = getArguments().getInt("MOVIE_ID");
        titleView = this.getActivity().findViewById(R.id.movie_title);
        imageView = this.getActivity().findViewById(R.id.entry_image);
        descriptionView = this.getActivity().findViewById(R.id.entry_description);
        popularityView = this.getActivity().findViewById(R.id.entry_popularity);
        reviewView = this.getActivity().findViewById(R.id.entry_user_rating);
        releaseDateView = this.getActivity().findViewById(R.id.entry_release_date);
        mReviewRecyclerView = this.getActivity().findViewById(R.id.review_list_recycle_view);
        mVideoRecyclerView = this.getActivity().findViewById(R.id.video_list_recycle_view);
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(mContext);
        LinearLayoutManager videoLayoutManager = new LinearLayoutManager(mContext);
        reviewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        videoLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mReviewRecyclerView.setLayoutManager(reviewLayoutManager);
        mVideoRecyclerView.setLayoutManager(videoLayoutManager);
        reviewRecyler = new MovieReviewRecylerAdapter();
        videoRecycler = new MovieVideoRecyclerAdapter();
        mReviewRecyclerView.setAdapter(reviewRecyler);
        mVideoRecyclerView.setAdapter(videoRecycler);
        saveButton  = this.getActivity().findViewById(R.id.save_as_favorite_button);

        mMovieData.observe(owner, new Observer<MovieInfo>() {
            @Override
            public void onChanged(@Nullable MovieInfo movieInfo) {
                new ViewMovieInfo().execute(movieInfo);
            }
        });
        mVideoData.observe(owner, new Observer<List<VideoData>>() {
            @Override
            public void onChanged(@Nullable List<VideoData> videoData) {
                videoRecycler.setData(videoData);
            }
        });
        mReviewData.observe(owner, new Observer<List<ReviewData>>() {
            @Override
            public void onChanged(@Nullable List<ReviewData> reviewData) {
                reviewRecyler.setData(reviewData);
            }
        });

        new LoadMovieInfo().execute(MovieID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MarkASFavorite().execute(MovieID);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        super.onDetach();
    }

    private class MarkASFavorite extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... movieid) {
            if (movieid.length > 0) {
                MovieDBRepository movieDB = MainActivity.movieDB;
                MovieInfo movieInfo = movieDB.getMovieInfo(movieid[0]);
                movieInfo.user_choice = (movieInfo.user_choice == 0)? 1 : 0;
                movieDB.insert(movieInfo);
                mMovieData.postValue(movieInfo);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    private class LoadMovieInfo extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            if (integers.length>0){
                MovieDBRepository movieDB = MainActivity.movieDB;
                mMovieData.postValue(movieDB.getMovieInfo(integers[0]));
                mVideoData.postValue(movieDB.getVideoofMovie(integers[0]));
                mReviewData.postValue(movieDB.getReviewofMovie(integers[0]));
            }
            return null;
        }

        @Override
        protected void onPostExecute( Void vo) {
            super.onPostExecute(vo);
        }
    }

    private class ViewMovieInfo extends AsyncTask<MovieInfo, Void, MovieInfo>{

        @Override
        protected MovieInfo doInBackground(MovieInfo... movieInfos) {
            MovieInfo movieInfo = null;
            if(movieInfos.length>0){
                movieInfo = movieInfos[0];
            }
            return movieInfo;
        }

        @Override
        protected void onPostExecute(MovieInfo movieInfo) {
            super.onPostExecute(movieInfo);
            if(movieInfo != null)
            Picasso.with(mContext).
                    load(MovieDBAPI.getApiImageUrl(movieInfo.posterpath)).
                    placeholder(R.mipmap.loading_image_place_holder).
                    error(R.mipmap.error_loading_image).
                    into(imageView);
            titleView.setText(movieInfo.movie_title);
            descriptionView.setText(movieInfo.overview);
            popularityView.setText(movieInfo.popularity.toString());
            reviewView.setText(movieInfo.avg_vote.toString());
            releaseDateView.setText(movieInfo.release_data.toString());
            saveButton.setText((movieInfo.user_choice == 0)? getString(R.string.mark_as_fav) : getString(R.string.remove_from_fav));
        }
    }

}
