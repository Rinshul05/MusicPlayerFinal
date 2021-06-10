package com.arjteam.music.playmusicpro.model.smartplaylist

import androidx.annotation.DrawableRes
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.model.AbsCustomPlaylist

abstract class AbsSmartPlaylist(
    name: String,
    @DrawableRes val iconRes: Int = R.drawable.ic_queue_music
) : AbsCustomPlaylist(
    id = PlaylistIdGenerator(name, iconRes),
    name = name
)