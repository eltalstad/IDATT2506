package com.example.oving2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oving2.R

class Task2Activity : Activity() {

    private val RANDOM_NUMBER_REQUEST_CODE1 = 1
    private val RANDOM_NUMBER_REQUEST_CODE2 = 2

    private var number1: Int = 3
    private var number2: Int = 5
    private var upperLimit: Int = 100

    private var number1TextView: TextView? = null
    private var number2TextView: TextView? = null
    private var answerTextView: TextView? = null
    private var upperLimitTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)

        number1TextView = findViewById<TextView>(R.id.display_number1)
        number2TextView = findViewById<TextView>(R.id.display_number2)
        answerTextView = findViewById<TextView>(R.id.answer)
        upperLimitTextView = findViewById<TextView>(R.id.upper_limit)

        number1TextView?.text = number1.toString()
        number2TextView?.text = number2.toString()
        upperLimitTextView?.text = upperLimit.toString()
    }

    fun addOnClick(v: View?) {
        val userAnswer = answerTextView?.text.toString().toIntOrNull() ?: 0
        val correctAnswer = number1 + number2
        checkAnswer(userAnswer, correctAnswer)
        requestRandomNumber(RANDOM_NUMBER_REQUEST_CODE1)
    }

    fun multiplyOnClick(v: View?) {
        val userAnswer = answerTextView?.text.toString().toIntOrNull() ?: 0
        val correctAnswer = number1 * number2
        checkAnswer(userAnswer, correctAnswer)
        requestRandomNumber(RANDOM_NUMBER_REQUEST_CODE1)
    }

    private fun checkAnswer(userAnswer: Int, correctAnswer: Int) {
        if (userAnswer == correctAnswer) {
            Toast.makeText(this, getString(R.string.correct), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.wrong) + " $correctAnswer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestRandomNumber(requestCode: Int) {
        val intent = Intent(this, RandomNumberActivity::class.java)
        intent.putExtra("UPPER_LIMIT", upperLimit)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RANDOM_NUMBER_REQUEST_CODE1 -> {
                    number1 = data?.getIntExtra("RANDOM_NUMBER", -1) ?: -1
                    number1TextView?.text = number1.toString()
                    requestRandomNumber(RANDOM_NUMBER_REQUEST_CODE2)
                }
                RANDOM_NUMBER_REQUEST_CODE2 -> {
                    number2 = data?.getIntExtra("RANDOM_NUMBER", -1) ?: -1
                    number2TextView?.text = number2.toString()
                }
            }
        }
    }
}