package com.example.test_four

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class profile1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Home button to navigate back to MainActivity
        val homeButton: Button = findViewById(R.id.home)
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
             showToast(this, "Welcome back home! Let’s continue your language adventure!", Toast.LENGTH_SHORT)
        }

        // Exercise button
        val exerciseButton: Button = findViewById(R.id.exercise)
        exerciseButton.setOnClickListener {
            val intent = Intent(this, exercise::class.java)
            startActivity(intent)
            showToast(this, "Ready for a challenge? Start your exercises now!", Toast.LENGTH_SHORT)
        }


        val a15Button: Button = findViewById(R.id.soon)
        a15Button.setOnClickListener {
            // Create an instance of the DialogFragment (using the correct class name 'a11')
            val dialogFragment = soon()

            // Show the dialog fragment
            dialogFragment.show(supportFragmentManager, "dialog_tag")
        }

    }

    private fun showToast(context: Context, message: CharSequence, duration: Int) {
        Toast.makeText(context, message, duration).show()
    }

}