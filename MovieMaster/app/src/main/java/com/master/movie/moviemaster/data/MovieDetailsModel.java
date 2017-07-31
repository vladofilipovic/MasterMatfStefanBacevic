package com.master.movie.moviemaster.data;

import android.util.Log;

import com.master.movie.moviemaster.database.DBHelper;
import com.master.movie.moviemaster.dto.MovieDetails;
import com.master.movie.moviemaster.internal.ApiServiceWrapper;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by stefan.bacevic on 7/22/2017.
 */

public class MovieDetailsModel {
    DBHelper dbHelper;

    ApiServiceWrapper apiServiceWrapper;

    public MovieDetailsModel(ApiServiceWrapper apiServiceWrapper, DBHelper dbHelper) {
        this.apiServiceWrapper = apiServiceWrapper;
        this.dbHelper = dbHelper;
    }

    public Observable<MovieDetails> getMovieDetails(int movieId) {
        Log.d("MyDebug", "getMovieDetails");
        return Observable.create((Observable.OnSubscribe<MovieDetails>) subscriber ->
                MovieDetailsModel.this.getMovieDetailsSub(subscriber, movieId))
                .doOnNext(movieDetails -> apiServiceWrapper.getPoster(movieDetails))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    private void getMovieDetailsSub(Subscriber<? super MovieDetails> subscriber, int movieId) {
        try {
            subscriber.onNext(apiServiceWrapper.getMovieDetails(movieId));
            subscriber.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
    }

    public void addToFavourites(MovieDetails movieDetails) {
        dbHelper.insertMovieToFavourites(movieDetails);
    }

    public void addToWatchlist(MovieDetails movieDetails) {
        dbHelper.insertMovieToWatchlist(movieDetails);
    }
}
