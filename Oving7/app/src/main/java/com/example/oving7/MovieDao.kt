package com.example.oving7

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE director = :director")
    fun getMoviesByDirector(director: String): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE actors like :actor")
    fun getMoviesByActor(actor: String): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: Movie)

    @Query("SELECT COUNT(*) FROM movie")
    fun getMoviesCount(): Int

    // Delete all movies from the database
    @Query("DELETE FROM movie")
     fun deleteAllMovies()
}