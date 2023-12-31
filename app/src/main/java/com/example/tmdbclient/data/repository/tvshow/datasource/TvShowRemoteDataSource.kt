package com.example.tmdbclient.data.repository.tvshow.datasource

import com.example.tmdbclient.data.model.artist.ArtistList
import com.example.tmdbclient.data.model.tvshow.TvShow
import com.example.tmdbclient.data.model.tvshow.TvShowList
import retrofit2.Response

interface TvShowRemoteDataSource {
    suspend fun getTvShows(): Response<TvShowList>
}