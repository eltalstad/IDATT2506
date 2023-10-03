package com.example.oving5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var cardNumberEditText: EditText
    private lateinit var startButton: Button
    private lateinit var guessEditText: EditText
    private lateinit var guessButton: Button
    private lateinit var messageTextView: TextView

    private var client = OkHttpClient()
    private val url = "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp"

    init {
        val cookieJar = object : CookieJar {
            private val cookieStore = HashMap<HttpUrl, List<Cookie>>()

            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url] = cookies
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                return cookieStore[url] ?: ArrayList()
            }
        }

        client = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameEditText = findViewById(R.id.nameEditText)
        cardNumberEditText = findViewById(R.id.cardNumberEditText)
        startButton = findViewById(R.id.startButton)
        guessEditText = findViewById(R.id.guessEditText)
        guessButton = findViewById(R.id.guessButton)
        messageTextView = findViewById(R.id.messageTextView)

        startButton.setOnClickListener { onStartButtonClicked() }
        guessButton.setOnClickListener { onGuessButtonClicked() }
    }

    private fun onStartButtonClicked() {
        val name = nameEditText.text.toString()
        val cardNumber = cardNumberEditText.text.toString()


        if (name.isNullOrBlank() || cardNumber.isNullOrBlank()) {
            messageTextView.text = "Please enter your name and card number"
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val formBody =
                    FormBody.Builder().add("navn", name).add("kortnummer", cardNumber).build()
                val request = Request.Builder().url(url).post(formBody).build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body?.string() ?: ""
                    CoroutineScope(Dispatchers.Main).launch {
                        messageTextView.text = responseBody
                        handleStartGameResponse(responseBody)
                    }
                }
            }
        }
    }

        private fun onGuessButtonClicked() {
            val guess = guessEditText.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val formBody = FormBody.Builder().add("tall", guess).build()
                val request = Request.Builder().url(url).post(formBody).build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseBody = response.body?.string() ?: ""
                    CoroutineScope(Dispatchers.Main).launch {
                        messageTextView.text = responseBody
                        handleGuessResponse(responseBody)
                    }
                }
            }
        }

        private fun handleStartGameResponse(response: String) {
            if (response.contains("Oppgi et tall mellom", ignoreCase = true)) {
                guessButton.visibility = Button.VISIBLE
                guessEditText.visibility = EditText.VISIBLE
            } else {
                messageTextView.text = response
            }
        }

        private fun handleGuessResponse(response: String) {
            messageTextView.text = response
        }
    }