package com.arjteam.music.playmusicpro.model.smartplaylist

import com.arjteam.music.playmusicpro.App
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.model.Song
import kotlinx.android.parcel.Parcelize

@Parcelize
class TopTracksPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.my_top_tracks),
    iconRes = R.drawable.ic_trending_up
) {
    override fun songs(): List<Song> {
        return topPlayedRepository.topTracks()
    }
}