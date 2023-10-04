package com.example.oving2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.example.oving2.ui.theme.Oving2Theme

class Task1Activity : Activity() {

    private val RANDOM_NUMBER_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task1)
    }

    fun onClickRandomNumberActivity(v: View?) {
        val intent = Intent(this, RandomNumberActivity::class.java)
        intent.putExtra("UPPER_LIMIT", 100)
        startActivityForResult(intent, RANDOM_NUMBER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RANDOM_NUMBER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val resultNumber = data?.getIntExtra("RANDOM_NUMBER", -1)
            
            Toast.makeText(this, "Received random number: $resultNumber", Toast.LENGTH_SHORT).show()

            val textView = findViewById<View>(R.id.textView) as TextView
            textView.text = "Received random number: $resultNumber"
        }
    }

    fun gotoTask2Activity(view: View) {
        val intent = Intent(this, Task2Activity::class.java)
        startActivity(intent)
    }
}