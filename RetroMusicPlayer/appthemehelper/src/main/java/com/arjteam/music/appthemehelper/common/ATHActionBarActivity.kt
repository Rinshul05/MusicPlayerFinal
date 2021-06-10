package com.arjteam.music.appthemehelper.common

import androidx.appcompat.widget.Toolbar

import com.arjteam.music.appthemehelper.util.ToolbarContentTintHelper

class ATHActionBarActivity : ATHToolbarActivity() {

    override fun getATHToolbar(): Toolbar? {
        return ToolbarContentTintHelper.getSupportActionBarView(supportActionBar)
    }
}
