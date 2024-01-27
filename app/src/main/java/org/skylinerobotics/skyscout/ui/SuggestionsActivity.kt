package org.skylinerobotics.skyscout.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.skylinerobotics.skyscout.R
import org.skylinerobotics.skyscout.data.SuggestionsDatabase

class SuggestionsActivity : AppCompatActivity() {
    private lateinit var submitButton: Button
    private lateinit var nameEntry: EditText
    private lateinit var suggestionEntry: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions)

        initLayoutElements()
        setClickListeners()
    }

    private fun initLayoutElements() {
        submitButton = findViewById(R.id.submit_button)
        nameEntry = findViewById(R.id.name_entry)
        suggestionEntry = findViewById(R.id.suggestion_entry)
    }

    private fun setClickListeners() {
        submitButton.setOnClickListener { submitButtonAction() }
    }

    private fun submitButtonAction() {
        val name = nameEntry.text.toString()
        val suggestion = suggestionEntry.text.toString()

        SuggestionsDatabase(this).use {
            it.addEntry(name, suggestion)
        }

        Toast.makeText(this, "Suggestion submitted. Thank you.", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
    }
}