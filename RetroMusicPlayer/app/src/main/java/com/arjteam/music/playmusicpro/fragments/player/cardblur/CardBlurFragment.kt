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
package com.arjteam.music.playmusicpro.fragments.player.cardblur

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.arjteam.music.appthemehelper.util.ToolbarContentTintHelper
import com.arjteam.music.playmusicpro.NEW_BLUR_AMOUNT
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.fragments.base.AbsPlayerFragment
import com.arjteam.music.playmusicpro.fragments.player.PlayerAlbumCoverFragment
import com.arjteam.music.playmusicpro.fragments.player.normal.PlayerFragment
import com.arjteam.music.playmusicpro.glide.BlurTransformation
import com.arjteam.music.playmusicpro.glide.RetroMusicColoredTarget
import com.arjteam.music.playmusicpro.glide.SongGlideRequest
import com.arjteam.music.playmusicpro.helper.MusicPlayerRemote
import com.arjteam.music.playmusicpro.model.Song
import com.arjteam.music.playmusicpro.util.color.MediaNotificationProcessor
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_card_blur_player.*

class CardBlurFragment : AbsPlayerFragment(R.layout.fragment_card_blur_player),
    SharedPreferences.OnSharedPreferenceChangeListener {
    override fun playerToolbar(): Toolbar {
        return playerToolbar
    }

    private var lastColor: Int = 0
    override val paletteColor: Int
        get() = lastColor
    private lateinit var playbackControlsFragment: CardBlurPlaybackControlsFragment

    override fun onShow() {
        playbackControlsFragment.show()
    }

    override fun onHide() {
        playbackControlsFragment.hide()
        onBackPressed()
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun toolbarIconColor(): Int {
        return Color.WHITE
    }

    override fun onColorChanged(color: MediaNotificationProcessor) {
        playbackControlsFragment.setColor(color)
        lastColor = color.backgroundColor
        libraryViewModel.updateColor(color.backgroundColor)
        ToolbarContentTintHelper.colorizeToolbar(playerToolbar, Color.WHITE, activity)

        playerToolbar.setTitleTextColor(Color.WHITE)
        playerToolbar.setSubtitleTextColor(Color.WHITE)
    }

    override fun toggleFavorite(song: Song) {
        super.toggleFavorite(song)
        if (song.id == MusicPlayerRemote.currentSong.id) {
            updateIsFavorite()
        }
    }

    override fun onFavoriteToggled() {
        toggleFavorite(MusicPlayerRemote.currentSong)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSubFragments()
        setUpPlayerToolbar()
    }

    private fun setUpSubFragments() {
        playbackControlsFragment =
            childFragmentManager.findFragmentById(R.id.playbackControlsFragment) as CardBlurPlaybackControlsFragment
        (childFragmentManager.findFragmentById(R.id.playerAlbumCoverFragment) as PlayerAlbumCoverFragment?)?.setCallbacks(
            this
        )
    }

    private fun setUpPlayerToolbar() {
        playerToolbar.apply {
            inflateMenu(R.menu.menu_player)
            setNavigationOnClickListener { requireActivity().onBackPressed() }
            setTitleTextColor(Color.WHITE)
            setSubtitleTextColor(Color.WHITE)
            ToolbarContentTintHelper.colorizeToolbar(playerToolbar, Color.WHITE, activity)
        }.setOnMenuItemClickListener(this)
    }

    override fun onServiceConnected() {
        updateIsFavorite()
        updateBlur()
        updateSong()
    }

    override fun onPlayingMetaChanged() {
        updateIsFavorite()
        updateBlur()
        updateSong()
    }

    private fun updateSong() {
        val song = MusicPlayerRemote.currentSong
        playerToolbar.apply {
            title = song.title
            subtitle = song.artistName
        }
    }

    private fun updateBlur() {
        val blurAmount = PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getInt(NEW_BLUR_AMOUNT, 25)
        colorBackground!!.clearColorFilter()
        SongGlideRequest.Builder.from(Glide.with(requireActivity()), MusicPlayerRemote.currentSong)
            .checkIgnoreMediaStore(requireContext())
            .generatePalette(requireContext()).build()
            .dontAnimate()
            .transform(
                BlurTransformation.Builder(requireContext()).blurRadius(blurAmount.toFloat())
                    .build()
            )
            .into(object : RetroMusicColoredTarget(colorBackground) {
                override fun onColorReady(colors: MediaNotificationProcessor) {
                    if (colors.backgroundColor == defaultFooterColor) {
                        colorBackground.setColorFilter(colors.backgroundColor)
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        PreferenceManager.getDefaultSharedPreferences(requireContext())
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == NEW_BLUR_AMOUNT) {
            updateBlur()
        }
    }

    companion object {
        fun newInstance(): PlayerFragment {
            return PlayerFragment()
        }
    }
}
