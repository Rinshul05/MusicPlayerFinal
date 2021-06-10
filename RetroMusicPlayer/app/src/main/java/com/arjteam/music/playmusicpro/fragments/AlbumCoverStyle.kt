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
package com.arjteam.music.playmusicpro.fragments

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.arjteam.music.playmusicpro.R

enum class AlbumCoverStyle(
    @StringRes val titleRes: Int,
    @DrawableRes val drawableResId: Int,
    val id: Int
) {
    Card(R.string.card, R.drawable.np_blur_card, 4),
    Circle(R.string.circular, R.drawable.np_circle, 2),
    Flat(R.string.flat, R.drawable.np_flat, 1),
    FullCard(R.string.full_card, R.drawable.np_adaptive, 6),
    Full(R.string.full, R.drawable.np_full, 5),
    Material(R.string.material, R.drawable.np_material, 3),
    Normal(R.string.normal, R.drawable.np_normal, 0),
}
