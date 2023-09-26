package com.example.oving2

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class RandomNumberActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_number)

        generateRandomNumber()
    }

    private fun generateRandomNumber() {
        val upperLimit = intent.getIntExtra("UPPER_LIMIT", 0)
        val randomNumber = (0..upperLimit).random()

        val resultIntent = Intent()
        resultIntent.putExtra("RANDOM_NUMBER", randomNumber)
        setResult(Activity.RESULT_OK, resultIntent)

        finish()
    }
}
