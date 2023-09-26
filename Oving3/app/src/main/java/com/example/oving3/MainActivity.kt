package com.example.oving3

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oving3.ui.theme.Oving3Theme

class MainActivity : Activity() {

    private lateinit var adapter: FriendAdapter
    private val friendList: MutableList<Friend> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = FriendAdapter(friendList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val birthdateEditText: EditText = findViewById(R.id.birthdateEditText)
        val addButton: Button = findViewById(R.id.addButton)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val birthdate = birthdateEditText.text.toString()

            if(name.isNotBlank() && birthdate.isNotBlank()) {
                friendList.add(Friend(name, birthdate))
                adapter.notifyDataSetChanged()

                nameEditText.text.clear()
                birthdateEditText.text.clear()
            } else {
                Toast.makeText(this, "Both fields are required!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}