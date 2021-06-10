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
package com.arjteam.music.playmusicpro.adapter.song

import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.db.PlaylistEntity
import com.arjteam.music.playmusicpro.db.toSongEntity
import com.arjteam.music.playmusicpro.dialogs.RemoveSongFromPlaylistDialog
import com.arjteam.music.playmusicpro.interfaces.ICabHolder
import com.arjteam.music.playmusicpro.model.Song

class PlaylistSongAdapter(
    private val playlist: PlaylistEntity,
    activity: FragmentActivity,
    dataSet: MutableList<Song>,
    itemLayoutRes: Int,
    iCabHolder: ICabHolder?
) : SongAdapter(activity, dataSet, itemLayoutRes, iCabHolder) {

    init {
        this.setMultiSelectMenuRes(R.menu.menu_cannot_delete_single_songs_playlist_songs_selection)
    }

    override fun createViewHolder(view: View): SongAdapter.ViewHolder {
        return ViewHolder(view)
    }

    open inner class ViewHolder(itemView: View) : SongAdapter.ViewHolder(itemView) {

        override var songMenuRes: Int
            get() = R.menu.menu_item_playlist_song
            set(value) {
                super.songMenuRes = value
            }

        override fun onSongMenuItemClick(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.action_remove_from_playlist -> {
                    RemoveSongFromPlaylistDialog.create(song.toSongEntity(playlist.playListId))
                        .show(activity.supportFragmentManager, "REMOVE_FROM_PLAYLIST")
                    return true
                }
            }
            return super.onSongMenuItemClick(item)
        }
    }
}
