package com.arjteam.music.playmusicpro.model

import com.arjteam.music.playmusicpro.repository.LastAddedRepository
import com.arjteam.music.playmusicpro.repository.SongRepository
import com.arjteam.music.playmusicpro.repository.TopPlayedRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class AbsCustomPlaylist(
    id: Long,
    name: String
) : Playlist(id, name), KoinComponent {

    abstract fun songs(): List<Song>

    protected val songRepository by inject<SongRepository>()

    protected val topPlayedRepository by inject<TopPlayedRepository>()

    protected val lastAddedRepository by inject<LastAddedRepository>()
}