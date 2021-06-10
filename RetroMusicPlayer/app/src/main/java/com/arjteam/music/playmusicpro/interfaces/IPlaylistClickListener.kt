package com.arjteam.music.playmusicpro.interfaces

import android.view.View
import com.arjteam.music.playmusicpro.db.PlaylistWithSongs

interface IPlaylistClickListener {
    fun onPlaylistClick(playlistWithSongs: PlaylistWithSongs, view: View)
}