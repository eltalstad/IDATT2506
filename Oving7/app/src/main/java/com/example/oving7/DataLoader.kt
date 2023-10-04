package com.example.oving7

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataLoader(private val context: Context) {
    fun loadMoviesFromJson(): List<Movie> {
        val inputStream = context.resources.openRawResource(R.raw.filmer)
        val json = inputStream.bufferedReader().use { it.readText() }
        val gson = Gson()

        val arrayMovieType = object : TypeToken<List<Movie>>() {}.type

        return gson.fromJson(json, arrayMovieType) ?: listOf()
    }
}
