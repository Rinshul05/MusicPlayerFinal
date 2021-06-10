package com.arjteam.music.playmusicpro.model.smartplaylist

import com.arjteam.music.playmusicpro.App
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.model.Song
import kotlinx.android.parcel.Parcelize

@Parcelize
class ShuffleAllPlaylist : AbsSmartPlaylist(
    name = App.getContext().getString(R.string.action_shuffle_all),
    iconRes = R.drawable.ic_shuffle
) {
    override fun songs(): List<Song> {
        return songRepository.songs()
    }
}