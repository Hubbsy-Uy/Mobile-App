package com.example.test_four

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction

class Taga2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taga2)

        // Handle window insets for modern devices (for system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up button listeners
        setupButtonListeners()
    }
    private fun showWrongAnsPopup() {
        val wrongAnsFragment = wrongans()
        wrongAnsFragment.show(supportFragmentManager, "WrongAnsDialog")
    }
    private fun showRightAnsPopup() {
        val wrongAnsFragment = rightanswer()
        wrongAnsFragment.show(supportFragmentManager, "WrongAnsDialog")
    }
    private fun setupButtonListeners() {

        val dictionaryButton: Button = findViewById(R.id.ans1)
        dictionaryButton.setOnClickListener {
            val intent = Intent(this, Taga3::class.java)
            showRightAnsPopup()
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent)
            }, 500)
        }

        // Continue button
        val exerciseButton: Button = findViewById(R.id.ans2)
        exerciseButton.setOnClickListener {
            val intent = Intent(this, Taga3::class.java)
            showWrongAnsPopup()
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent)
            }, 500)
        }
        val exButton: Button = findViewById(R.id.ans3)
        exButton.setOnClickListener {
            val intent = Intent(this, Taga3::class.java)
            showWrongAnsPopup()
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(intent)
            }, 500)
        }
    }
}