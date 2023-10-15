package com.example.tmdbclient.data.repository.artist

import android.util.Log
import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.data.repository.artist.datasource.ArtistCacheDataSource
import com.example.tmdbclient.data.repository.artist.datasource.ArtistLocalDataSource
import com.example.tmdbclient.data.repository.artist.datasource.ArtistRemoteDataSource
import com.example.tmdbclient.domain.repository.ArtistRepository

class ArtistRepositoryImpl(
    private val artistRemoteDataSource: ArtistRemoteDataSource,
    private val artistLocalDataSource: ArtistLocalDataSource,
    private val artistCacheDataSource: ArtistCacheDataSource
) : ArtistRepository {

    override suspend fun getArtists(): List<Artist>? {
        return getArtistsFromCache();
    }

    override suspend fun updateArtists(): List<Artist>? {
        val newListOfArtist = getArtistFromAPI()
        artistLocalDataSource.clearAll()
        artistLocalDataSource.saveArtistsToDB(newListOfArtist)
        artistCacheDataSource.saveArtistToCache(newListOfArtist)
        return newListOfArtist
    }


    private suspend fun getArtistFromAPI(): List<Artist> {
        lateinit var artistList: List<Artist>
        try {
            val response = artistRemoteDataSource.getArtists()
            val body = response.body()
            if (body != null) {
                artistList = body.artists
            }
        } catch (exception: Exception) {
            Log.i("TAG", exception.toString())
        }
        return artistList
    }

    private suspend fun getArtistsFromDB(): List<Artist> {
        lateinit var artistList: List<Artist>
        try {
            artistList = artistLocalDataSource.getArtistsFromDB()
        } catch (exception: Exception) {
            Log.i("TAG", exception.toString())
        }
        if (artistList.isNotEmpty()) {
            return artistList
        } else {
            artistList = getArtistFromAPI()
            artistLocalDataSource.saveArtistsToDB(artistList)
        }
        return artistList
    }

    private suspend fun getArtistsFromCache(): List<Artist> {
        lateinit var artistList: List<Artist>
        try {
            artistList = artistCacheDataSource.getArtistFromCache()
        } catch (exception: Exception) {
            Log.i("TAG", exception.toString())
        }
        if (artistList.isNotEmpty()) {
            return artistList
        } else {
            artistList = getArtistsFromDB()
            artistCacheDataSource.saveArtistToCache(artistList)
        }
        return artistList
    }

}