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
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.arjteam.music.appthemehelper.ThemeStore
import com.arjteam.music.appthemehelper.util.MaterialUtil
import com.arjteam.music.playmusicpro.App
import com.arjteam.music.playmusicpro.BuildConfig
import com.arjteam.music.playmusicpro.Constants.PRO_VERSION_PRODUCT_ID
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.activities.base.AbsBaseActivity

import java.lang.ref.WeakReference
import kotlinx.android.synthetic.main.activity_pro_version.*

class PurchaseActivity : AbsBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setDrawUnderStatusBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pro_version)
        setStatusbarColor(Color.TRANSPARENT)
        setLightStatusbar(false)
        setNavigationbarColor(Color.BLACK)
        setLightNavigationBar(false)
        toolbar.navigationIcon?.setTint(Color.WHITE)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        restoreButton.isEnabled = false
        purchaseButton.isEnabled = false

        bannerContainer.backgroundTintList =
            ColorStateList.valueOf(ThemeStore.accentColor(this))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {

        super.onDestroy()
    }


    companion object {
        private const val TAG: String = "PurchaseActivity"
    }
}
