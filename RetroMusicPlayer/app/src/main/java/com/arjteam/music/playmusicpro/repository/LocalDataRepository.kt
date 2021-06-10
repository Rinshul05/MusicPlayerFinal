package com.arjteam.music.playmusicpro.repository

import android.content.Context
import com.arjteam.music.playmusicpro.model.Contributor
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

interface LocalDataRepository {
    fun contributors(): List<Contributor>
}

class RealLocalDataRepository(
    private val context: Context
) : LocalDataRepository {

    override fun contributors(): List<Contributor> {
        val jsonString = context.assets.open("contributors.json")
            .bufferedReader().use { it.readText() }

        val gsonBuilder = GsonBuilder()
        val gson = gsonBuilder.create()
        val listContributorType = object : TypeToken<List<Contributor>>() {}.type
        return gson.fromJson(jsonString, listContributorType)
    }
}