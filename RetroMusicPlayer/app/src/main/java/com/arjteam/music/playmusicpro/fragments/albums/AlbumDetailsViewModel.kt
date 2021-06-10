/*
 * Copyright (c) 2020 Hemanth Savarla.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 */
package com.arjteam.music.playmusicpro.fragments.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.arjteam.music.playmusicpro.interfaces.IMusicServiceEventListener
import com.arjteam.music.playmusicpro.model.Album
import com.arjteam.music.playmusicpro.model.Artist
import com.arjteam.music.playmusicpro.network.Result
import com.arjteam.music.playmusicpro.network.model.LastFmAlbum
import com.arjteam.music.playmusicpro.repository.RealRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class AlbumDetailsViewModel(
    private val repository: RealRepository,
    private val albumId: Long
) : ViewModel(), IMusicServiceEventListener {
    private val albumDetails = MutableLiveData<Album>()

    init {
        fetchAlbum()
    }

    private fun fetchAlbum() {
        viewModelScope.launch(IO) {
            albumDetails.postValue(repository.albumByIdAsync(albumId))
        }
    }

    fun getAlbum(): LiveData<Album> = albumDetails

    fun getArtist(artistId: Long): LiveData<Artist> = liveData(IO) {
        val artist = repository.artistById(artistId)
        emit(artist)
    }

    fun getAlbumInfo(album: Album): LiveData<Result<LastFmAlbum>> = liveData {
        emit(Result.Loading)
        emit(repository.albumInfo(album.artistName ?: "-", album.title ?: "-"))
    }

    fun getMoreAlbums(artist: Artist): LiveData<List<Album>> = liveData(IO) {
        artist.albums.filter { item -> item.id != albumId }.let { albums ->
            if (albums.isNotEmpty()) emit(albums)
        }
    }

    override fun onMediaStoreChanged() {
        fetchAlbum()
    }

    override fun onServiceConnected() {}
    override fun onServiceDisconnected() {}
    override fun onQueueChanged() {}
    override fun onPlayingMetaChanged() {}
    override fun onPlayStateChanged() {}
    override fun onRepeatModeChanged() {}
    override fun onShuffleModeChanged() {}
}
