package com.example.test_four

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Taga1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taga1)

        // Handle window insets for modern devices (for system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up button listeners
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        // Back button
        val dictionaryButton: Button = findViewById(R.id.back)
        dictionaryButton.setOnClickListener {
            val intent = Intent(this, Tagalog::class.java) // Navigate to Tagalog Activity
            startActivity(intent)
        }

        // Continue button
        val exerciseButton: Button = findViewById(R.id.cont)
        exerciseButton.setOnClickListener {
            val intent = Intent(this, Taga2::class.java) // Navigate to Taga2 Activity
            startActivity(intent)
        }
    }
}
