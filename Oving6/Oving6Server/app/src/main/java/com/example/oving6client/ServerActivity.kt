package com.example.oving6client

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class ServerActivity : ComponentActivity() {

    private val clients = mutableListOf<ClientHandler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread {
           try {
               val serverSocket = ServerSocket(12345)
               while (true) {
                   val clientSocket = serverSocket.accept()
                   val clientHandler = ClientHandler(clientSocket, clients, ::updateUI)
                   clients.add(clientHandler)
               }
           } catch (e: Exception) {
               e.printStackTrace()
           }
        }
    }

    private fun updateUI(message: String) {
        runOnUiThread {
            val textMessages: TextView = findViewById(R.id.txtMessages)
            textMessages.append("\n$message")
        }
    }
}