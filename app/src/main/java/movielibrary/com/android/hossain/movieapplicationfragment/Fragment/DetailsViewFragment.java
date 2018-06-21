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
import android.widget.Toast;

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


    public DetailsViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("DETAILS", String.valueOf(getArguments().getInt("MOVIE_ID")));
        mContext = getContext();
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
        new LoadMovieInfo(this).execute(MovieID);
        Button saveButton  = this.getActivity().findViewById(R.id.save_as_favorite_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MarkASFavorite().execute(MovieID);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_view, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class MarkASFavorite extends AsyncTask<Integer, Void, Void>{

        @Override
        protected Void doInBackground(Integer... movieid) {
            if (movieid.length > 0) {
                MovieDBRepository movieDB = MainActivity.movieDB;
                MovieInfo mMovieData = movieDB.getMovieDetailsInfo(movieid[0]);
                mMovieData.user_choice = (mMovieData.user_choice == 0)? 1 : 0;
                movieDB.insert(mMovieData);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(mContext, "Your Movie is stored as fav", Toast.LENGTH_LONG);
        }
    }


    private class LoadMovieInfo extends AsyncTask<Integer, Void, Void> {
        LifecycleOwner owner;
        MovieInfo mMovieData;
//        Application mApplication;
        MutableLiveData<List<VideoData>> mVideoData;
        MutableLiveData<List<ReviewData>> mReviewData;

        LoadMovieInfo(LifecycleOwner owner){
            this.owner = owner;
//            this.mApplication = application;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            if (integers.length>0){
                MovieDBRepository movieDB = MainActivity.movieDB;
                mMovieData = movieDB.getMovieDetailsInfo(integers[0]);
                mVideoData = movieDB.getVideoofMovie(integers[0]);
                mReviewData = movieDB.getReviewofMovie(integers[0]);
//                onResume();
            }
            return null;
        }

        @Override
        protected void onPostExecute( Void vo) {
            super.onPostExecute(vo);
            titleView.setText(mMovieData.movie_title);
            Picasso.with(mContext).
                    load(MovieDBAPI.getApiImageUrl(mMovieData.posterpath)).
                    placeholder(R.mipmap.loading_image_place_holder).
                    error(R.mipmap.error_loading_image).
                    into(imageView);
            descriptionView.setText(mMovieData.overview);
            popularityView.setText(mMovieData.popularity.toString());
            reviewView.setText(mMovieData.avg_vote.toString());
            releaseDateView.setText(mMovieData.release_data.toString());
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
        }
    }

//    private class LoadVideoData extends AsyncTask<>
}
