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
package com.arjteam.music.playmusicpro.fragments.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjteam.music.playmusicpro.interfaces.IMusicServiceEventListener
import com.arjteam.music.playmusicpro.model.Genre
import com.arjteam.music.playmusicpro.model.Song
import com.arjteam.music.playmusicpro.repository.RealRepository
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenreDetailsViewModel(
    private val realRepository: RealRepository,
    private val genre: Genre
) : ViewModel(), IMusicServiceEventListener {

    private val _playListSongs = MutableLiveData<List<Song>>()
    private val _genre = MutableLiveData<Genre>().apply {
        postValue(genre)
    }

    fun getSongs(): LiveData<List<Song>> = _playListSongs

    fun getGenre(): LiveData<Genre> = _genre

    init {
        loadGenreSongs(genre)
    }

    private fun loadGenreSongs(genre: Genre) = viewModelScope.launch {
        val songs = realRepository.getGenre(genre.id)
        withContext(Main) { _playListSongs.postValue(songs) }
    }

    override fun onMediaStoreChanged() {
        loadGenreSongs(genre)
    }

    override fun onServiceConnected() {}
    override fun onServiceDisconnected() {}
    override fun onQueueChanged() {}
    override fun onPlayingMetaChanged() {}
    override fun onPlayStateChanged() {}
    override fun onRepeatModeChanged() {}
    override fun onShuffleModeChanged() {}
}
