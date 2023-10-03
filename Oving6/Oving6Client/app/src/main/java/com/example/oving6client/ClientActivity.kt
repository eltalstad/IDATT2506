package com.example.oving6client

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oving6client.ui.theme.Oving6ClientTheme
import java.io.BufferedWriter
import java.net.Socket
import kotlin.concurrent.thread

class ClientActivity : AppCompatActivity() {

    private lateinit var textMessages: TextView
    private lateinit var editTextMessage: EditText
    private lateinit var buttonSend: Button

    private lateinit var writer: BufferedWriter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textMessages = findViewById(R.id.textMessages)
        editTextMessage = findViewById(R.id.editTextMessage)
        buttonSend = findViewById(R.id.buttonSend)

        thread {
            try {
                val socket = Socket("10.0.2.2", 12345)
                val reader = socket.getInputStream().bufferedReader()
                writer = socket.getOutputStream().bufferedWriter()

                while(true) {
                    val serverMessage = reader.readLine()
                    runOnUiThread {
                        textMessages.append("\nServer: $serverMessage")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    textMessages.append("Error: ${e.message}")
                }
            }
        }

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotBlank()) 7
            thread {
                    try {
                        writer.write(message + "\n")
                        writer.flush()
                        runOnUiThread {
                            textMessages.append("\nClient: $message")
                            editTextMessage.text.clear()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            textMessages.append("Error: ${e.message}")
                        }
                    }
                }
        }
    }
}

