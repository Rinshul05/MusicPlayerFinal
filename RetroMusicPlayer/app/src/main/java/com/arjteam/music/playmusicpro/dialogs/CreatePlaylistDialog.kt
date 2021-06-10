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
package com.arjteam.music.playmusicpro.dialogs

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.arjteam.music.playmusicpro.EXTRA_SONG
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.extensions.colorButtons
import com.arjteam.music.playmusicpro.extensions.extra
import com.arjteam.music.playmusicpro.extensions.materialDialog
import com.arjteam.music.playmusicpro.fragments.LibraryViewModel
import com.arjteam.music.playmusicpro.model.Song
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.dialog_playlist.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CreatePlaylistDialog : DialogFragment() {
    private val libraryViewModel by sharedViewModel<LibraryViewModel>()

    companion object {
        fun create(song: Song): CreatePlaylistDialog {
            val list = mutableListOf<Song>()
            list.add(song)
            return create(list)
        }

        fun create(songs: List<Song>): CreatePlaylistDialog {
            return CreatePlaylistDialog().apply {
                arguments = bundleOf(EXTRA_SONG to songs)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_playlist, null)
        val songs: List<Song> = extra<List<Song>>(EXTRA_SONG).value ?: emptyList()
        val playlistView: TextInputEditText = view.actionNewPlaylist
        val playlistContainer: TextInputLayout = view.actionNewPlaylistContainer
        return materialDialog(R.string.new_playlist_title)
            .setView(view)
            .setPositiveButton(
                R.string.create_action
            ) { _, _ ->
                val playlistName = playlistView.text.toString()
                if (!TextUtils.isEmpty(playlistName)) {
                    libraryViewModel.addToPlaylist(playlistName, songs)

                } else {
                    playlistContainer.error = "Playlist is can't be empty"
                }
            }
            .create()
            .colorButtons()
    }
}
