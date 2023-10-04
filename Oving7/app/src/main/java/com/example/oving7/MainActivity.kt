package com.example.oving7

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.insertMoviesIntoDatabase()

        setUpSpinner()

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        movieAdapter = MovieAdapter(emptyList())
        recyclerView.adapter = movieAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
        val savedColor = sharedPreferences.getInt("color", resources.getColor(R.color.white, theme))
        val mainLayout: ConstraintLayout = findViewById(R.id.main_layout)
        mainLayout.setBackgroundColor(savedColor)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change -> {
                val intent = Intent(this, ColorActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUpSpinner() {
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.data_options, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selection = parent.getItemAtPosition(position).toString()
                when(selection) {
                    "All Movies" -> {
                        updateDataBasedOnSelection(selection)
                    }
                    else -> {
                        showInputDialog(selection)
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optional: Handle the case where nothing is selected if necessary
            }
        }
    }

    private fun showInputDialog(selection: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter ${if (selection == "Movies By Director") "Director" else "Actor Name"}")

        val input = EditText(this)
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            updateDataBasedOnSelection(selection, input.text.toString())
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        builder.show()
    }

    private fun updateDataBasedOnSelection(selection: String, userInput: String = "") {
        when (selection) {
            "All Movies" -> {
                viewModel.getMoviesFromDatabase().observe(this) { movies ->
                    movieAdapter.updateMovies(movies)
                }
            }
            "Movies By Director" -> {
                viewModel.getMoviesByDirector(userInput).observe(this) { movies ->
                    movieAdapter.updateMovies(movies)
                }
            }
            "Movies By Actor" -> {
                viewModel.getMoviesByActor(userInput).observe(this) { movies ->
                    movieAdapter.updateMovies(movies)
                }
            }
        }
    }
}