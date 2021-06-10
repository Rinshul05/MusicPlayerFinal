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
package com.arjteam.music.playmusicpro.activities

import android.content.Intent
import android.graphics.Paint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjteam.music.appthemehelper.ThemeStore
import com.arjteam.music.appthemehelper.util.ATHUtil
import com.arjteam.music.appthemehelper.util.TintHelper
import com.arjteam.music.appthemehelper.util.ToolbarContentTintHelper
import com.arjteam.music.playmusicpro.BuildConfig
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.activities.base.AbsBaseActivity
import com.arjteam.music.playmusicpro.extensions.textColorPrimary
import com.arjteam.music.playmusicpro.extensions.textColorSecondary

import java.lang.ref.WeakReference
import java.util.*
import kotlinx.android.synthetic.main.activity_donation.*

class SupportDevelopmentActivity : AbsBaseActivity(){

    companion object {
        val TAG: String = SupportDevelopmentActivity::class.java.simpleName
        const val DONATION_PRODUCT_IDS = R.array.donation_ids
        private const val TEZ_REQUEST_CODE = 123
    }


    private var skuDetailsLoadAsyncTask: AsyncTask<*, *, *>? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun donate(i: Int) {
        val ids = resources.getStringArray(DONATION_PRODUCT_IDS)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)

        setStatusbarColorAuto()
        setNavigationbarColorAuto()
        setTaskDescriptionColorAuto()
        setLightNavigationBar(true)

        setupToolbar()


        TintHelper.setTint(progress, ThemeStore.accentColor(this))
        donation.setTextColor(ThemeStore.accentColor(this))
    }

    private fun setupToolbar() {
        val toolbarColor = ATHUtil.resolveColor(this, R.attr.colorSurface)
        toolbar.setBackgroundColor(toolbarColor)
        ToolbarContentTintHelper.colorBackButton(toolbar)
        setSupportActionBar(toolbar)
    }


    override fun onDestroy() {
        super.onDestroy()
    }


}
