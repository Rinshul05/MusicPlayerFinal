package com.arjteam.music.playmusicpro.interfaces

import com.arjteam.music.playmusicpro.model.Album
import com.arjteam.music.playmusicpro.model.Artist
import com.arjteam.music.playmusicpro.model.Genre

interface IHomeClickListener {
    fun onAlbumClick(album: Album)

    fun onArtistClick(artist: Artist)

    fun onGenreClick(genre: Genre)
}