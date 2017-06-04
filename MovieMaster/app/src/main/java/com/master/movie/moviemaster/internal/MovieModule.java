package com.master.movie.moviemaster.internal;

import com.master.movie.moviemaster.data.MainMovieListModel;
import com.master.movie.moviemaster.mainmovielist.MainMovieListPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by stefa on 6/4/2017.
 */

@Module
public class MovieModule {

    @Provides
    @Singleton
    MainMovieListModel provideMainMovieListModel() {
        return new MainMovieListModel();
    }

    @Provides
    MainMovieListPresenter provideMainMovieListPresenter(MainMovieListModel model){
        return new MainMovieListPresenter(model);
    }

}
