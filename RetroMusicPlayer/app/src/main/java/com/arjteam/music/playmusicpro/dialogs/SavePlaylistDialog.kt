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
import android.media.MediaScannerConnection
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.arjteam.music.playmusicpro.App
import com.arjteam.music.playmusicpro.EXTRA_PLAYLIST
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.db.PlaylistWithSongs
import com.arjteam.music.playmusicpro.extensions.colorButtons
import com.arjteam.music.playmusicpro.extensions.extraNotNull
import com.arjteam.music.playmusicpro.extensions.materialDialog
import com.arjteam.music.playmusicpro.util.PlaylistsUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavePlaylistDialog : DialogFragment() {
    companion object {
        fun create(playlistWithSongs: PlaylistWithSongs): SavePlaylistDialog {
            return SavePlaylistDialog().apply {
                arguments = bundleOf(
                    EXTRA_PLAYLIST to playlistWithSongs
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            val playlistWithSongs = extraNotNull<PlaylistWithSongs>(EXTRA_PLAYLIST).value
            val file = PlaylistsUtil.savePlaylistWithSongs(playlistWithSongs)
            MediaScannerConnection.scanFile(
                requireActivity(),
                arrayOf<String>(file.path),
                null
            ) { _, _ ->
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    requireContext(),
                    String.format(App.getContext().getString(R.string.saved_playlist_to), file),
                    Toast.LENGTH_LONG
                ).show()
                dismiss()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return materialDialog(R.string.save_playlist_title)
            .setView(R.layout.loading)
            .create().colorButtons()
    }
}
