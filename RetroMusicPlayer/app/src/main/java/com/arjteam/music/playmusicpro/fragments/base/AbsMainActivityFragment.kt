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
package com.arjteam.music.playmusicpro.fragments.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.arjteam.music.appthemehelper.util.ATHUtil
import com.arjteam.music.appthemehelper.util.ColorUtil
import com.arjteam.music.appthemehelper.util.VersionUtils
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.activities.MainActivity
import com.arjteam.music.playmusicpro.fragments.LibraryViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class AbsMainActivityFragment(@LayoutRes layout: Int) : AbsMusicServiceFragment(layout) {
    val libraryViewModel: LibraryViewModel by sharedViewModel()

    val mainActivity: MainActivity
        get() = activity as MainActivity

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mainActivity.setNavigationbarColorAuto()
        mainActivity.setLightNavigationBar(true)
        mainActivity.setTaskDescriptionColorAuto()
        mainActivity.hideStatusBar()
    }

    private fun setStatusBarColor(view: View, color: Int) {
        val statusBar = view.findViewById<View>(R.id.status_bar)
        if (statusBar != null) {
            if (VersionUtils.hasMarshmallow()) {
                statusBar.setBackgroundColor(color)
                mainActivity.setLightStatusbarAuto(color)
            } else {
                statusBar.setBackgroundColor(color)
            }
        }
    }

    fun setStatusBarColorAuto(view: View) {
        val colorPrimary = ATHUtil.resolveColor(requireContext(), R.attr.colorSurface)
        // we don't want to use statusbar color because we are doing the color darkening on our own to support KitKat
        if (VersionUtils.hasMarshmallow()) {
            setStatusBarColor(view, colorPrimary)
        } else {
            setStatusBarColor(view, ColorUtil.darkenColor(colorPrimary))
        }
    }
}
