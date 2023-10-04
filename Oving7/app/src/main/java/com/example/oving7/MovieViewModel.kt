package com.example.oving7

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val dataLoader = DataLoader(application)
    private val database = Room.databaseBuilder(application, AppDatabase::class.java, "movies").build()

    fun insertMoviesIntoDatabase() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val movies = dataLoader.loadMoviesFromJson()
            database.movieDao().deleteAllMovies() // Add this line to clear existing records
            database.movieDao().insertAll(*movies.toTypedArray())
        }
        // Write movies to local file
        val movies = database.movieDao().getAllMovies().value
        if (movies != null) {
            writeMoviesToLocalFile(movies)
        }
    }


    private fun writeMoviesToLocalFile(movies: List<Movie>) = viewModelScope.launch {
        val fileOutput = getApplication<Application>().openFileOutput("movies.json", Context.MODE_PRIVATE)
        val outputStreamWriter = OutputStreamWriter(fileOutput)
        outputStreamWriter.use { it.write(Gson().toJson(movies)) }
    }

    fun getMoviesFromDatabase(): LiveData<List<Movie>> {
        return database.movieDao().getAllMovies()
    }

    fun getMoviesByDirector(director: String): LiveData<List<Movie>> {
        return database.movieDao().getMoviesByDirector(director)
    }

    fun getMoviesByActor(actor: String): LiveData<List<Movie>> {
        return database.movieDao().getMoviesByActor("%$actor%")
    }
}