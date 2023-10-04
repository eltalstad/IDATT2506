package com.example.oving7

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oving7.ui.theme.Oving7Theme

class ColorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)

        val btnRed: Button = findViewById(R.id.btn_red)
        val btnGreen: Button = findViewById(R.id.btn_green)
        val btnBlue: Button = findViewById(R.id.btn_blue)
        val btnReset: Button = findViewById(R.id.btn_reset)

        btnRed.setOnClickListener { saveColorAndFinish(R.color.red) }
        btnGreen.setOnClickListener { saveColorAndFinish(R.color.green) }
        btnBlue.setOnClickListener { saveColorAndFinish(R.color.blue) }
        btnReset.setOnClickListener { saveColorAndFinish(R.color.white) }
    }

    private fun saveColorAndFinish(colorRes: Int) {
        val sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("color", resources.getColor(colorRes, theme))
        editor.apply()
        finish()
    }
}