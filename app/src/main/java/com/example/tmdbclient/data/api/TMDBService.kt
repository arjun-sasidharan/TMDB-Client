package com.example.tmdbclient.data.api

import com.example.tmdbclient.data.ArtistList
import com.example.tmdbclient.data.MovieList
import com.example.tmdbclient.data.TvShowList
import retrofit2.Response
import retrofit2.http.GET

interface TMDBService {

    @GET("/movie/popular")
    suspend fun getPopularMovies(): Response<MovieList>

    @GET("/tv/popular")
    suspend fun getPopularTvShows(): Response<TvShowList>

    @GET("/person/popular")
    suspend fun getPopularArtist(): Response<ArtistList>
}