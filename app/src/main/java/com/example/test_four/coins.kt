package com.example.test_four

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class coins : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_coins)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val homeButton: Button = findViewById(R.id.home)
        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val a11Button: Button = findViewById(R.id.a11)
        a11Button.setOnClickListener {
            // Create an instance of the DialogFragment (using the correct class name 'a11')
            val dialogFragment = a11()

            // Show the dialog fragment
            dialogFragment.show(supportFragmentManager, "dialog_tag")
        }
        val a12Button: Button = findViewById(R.id.a12)
        a12Button.setOnClickListener {
            // Create an instance of the DialogFragment (using the correct class name 'a11')
            val dialogFragment = a12()

            // Show the dialog fragment
            dialogFragment.show(supportFragmentManager, "dialog_tag")
        }
        val a13Button: Button = findViewById(R.id.a13)
        a13Button.setOnClickListener {
            // Create an instance of the DialogFragment (using the correct class name 'a11')
            val dialogFragment = a13()

            // Show the dialog fragment
            dialogFragment.show(supportFragmentManager, "dialog_tag")
        }
        val a14Button: Button = findViewById(R.id.a14)
        a14Button.setOnClickListener {
            // Create an instance of the DialogFragment (using the correct class name 'a11')
            val dialogFragment = a14()

            // Show the dialog fragment
            dialogFragment.show(supportFragmentManager, "dialog_tag")
        }
        val a15Button: Button = findViewById(R.id.a15)
        a15Button.setOnClickListener {
            // Create an instance of the DialogFragment (using the correct class name 'a11')
            val dialogFragment = a15()

            // Show the dialog fragment
            dialogFragment.show(supportFragmentManager, "dialog_tag")
        }
        val a16Button: Button = findViewById(R.id.a16)
        a16Button.setOnClickListener {
            // Create an instance of the DialogFragment (using the correct class name 'a11')
            val dialogFragment = a16()

            // Show the dialog fragment
            dialogFragment.show(supportFragmentManager, "dialog_tag")
        }
    }
}