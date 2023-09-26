package com.example.oving3

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FriendAdapter(private val friendList: MutableList<Friend>, private val context: Context) :
    RecyclerView.Adapter<FriendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val friend = friendList[position]
        holder.nameTextView.text = friend.name
        holder.birthdateTextView.text = friend.birthdate
        holder.editButton.setOnClickListener {
            showEditDialog(position)
        }
    }

    override fun getItemCount() = friendList.size

    private fun showEditDialog(position: Int) {
        val friend = friendList[position]

        val layout = LayoutInflater.from(context).inflate(R.layout.friend_edit_dialog, null)
        val nameEdit: EditText = layout.findViewById(R.id.editNameEditText)
        val birthdateEdit: EditText = layout.findViewById(R.id.editBirthdateEditText)

        nameEdit.setText(friend.name)
        birthdateEdit.setText(friend.birthdate)

        AlertDialog.Builder(context)
            .setTitle("Edit Friend")
            .setView(layout)
            .setPositiveButton("Update") { _, _ ->
                val newName = nameEdit.text.toString()
                val newBirthdate = birthdateEdit.text.toString()

                if (newName.isNotBlank() && newBirthdate.isNotBlank()) {
                    friend.name = newName
                    friend.birthdate = newBirthdate

                    notifyItemChanged(position)
                } else {
                    Toast.makeText(context, "Both fields are required!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val birthdateTextView: TextView = view.findViewById(R.id.birthdateTextView)
        val editButton: Button = view.findViewById(R.id.editButton)
    }
}
