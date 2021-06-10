package com.arjteam.music.playmusicpro.model.smartplaylist

import com.arjteam.music.playmusicpro.App
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.model.Song
import kotlinx.android.parcel.Parcelize

@Parcelize
class LastAddedPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.last_added),
    iconRes = R.drawable.ic_library_add
) {
    override fun songs(): List<Song> {
        return lastAddedRepository.recentSongs()
    }
}