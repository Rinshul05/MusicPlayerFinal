package com.arjteam.music.playmusicpro.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.FrameLayout
import com.arjteam.music.appthemehelper.ThemeStore
import com.arjteam.music.appthemehelper.util.ColorUtil
import com.arjteam.music.playmusicpro.R
import com.arjteam.music.playmusicpro.extensions.accentOutlineColor
import kotlinx.android.synthetic.main.item_permission.view.*

class PermissionItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
    defStyleRes: Int = -1
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PermissionItem, 0, 0)
        inflate(context, R.layout.item_permission, this)

        title.text = attributes.getText(R.styleable.PermissionItem_permissionTitle)
        summary.text = attributes.getText(R.styleable.PermissionItem_permissionTitleSubTitle)
        number.text = attributes.getText(R.styleable.PermissionItem_permissionTitleNumber)
        button.text = attributes.getText(R.styleable.PermissionItem_permissionButtonTitle)
        button.setIconResource(
            attributes.getResourceId(
                R.styleable.PermissionItem_permissionIcon,
                R.drawable.ic_album
            )
        )
        val color = ThemeStore.accentColor(context)
        number.backgroundTintList = ColorStateList.valueOf(ColorUtil.withAlpha(color, 0.22f))

        button.accentOutlineColor()
        attributes.recycle()
    }

    fun setButtonClick(callBack: () -> Unit) {
        button.setOnClickListener { callBack.invoke() }
    }
}