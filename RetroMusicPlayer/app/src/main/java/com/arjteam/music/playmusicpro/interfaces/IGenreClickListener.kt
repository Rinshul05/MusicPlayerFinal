package com.arjteam.music.playmusicpro.interfaces

import android.view.View
import com.arjteam.music.playmusicpro.model.Genre

interface IGenreClickListener {
    fun onClickGenre(genre: Genre, view: View)
}