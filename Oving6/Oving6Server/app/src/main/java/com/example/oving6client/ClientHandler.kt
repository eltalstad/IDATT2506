package com.example.oving6client

import java.net.Socket
import kotlin.concurrent.thread

class ClientHandler(private val socket: Socket, private val clients: MutableList<ClientHandler>, private val uiUpdater: (String) -> Unit) {

    private val reader = socket.getInputStream().bufferedReader()
    private val writer = socket.getOutputStream().bufferedWriter()

    init {
        thread {
            try {
                writer.write("Hello, client!\n")
                writer.flush()

                while (true) {
                    val message = reader.readLine() ?: break
                    uiUpdater(message)
                    broadcast(message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                clients.remove(this)
                socket.close()
            }
        }
    }

    private fun broadcast(message: String) {
        clients.forEach { client -> if(client != this) {
            try {
                client.writer.write("$message\n")
                client.writer.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            }
        }

    }

}