//the last page for the meantime

package com.example.test_four

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ThirdPage : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.lesson_one)

        // mark the current lesson as completed
        val sharedPreferences = getSharedPreferences("LessonPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Lesson1Completed", true) // mark Lesson 1 as completed
        editor.apply()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // set up the back button
        findViewById<Button>(R.id.button9).setOnClickListener {
            //check if all lessons are completed
            val lesson2Completed = sharedPreferences.getBoolean("Lesson2Completed", false)
            val lesson3Completed = sharedPreferences.getBoolean("Lesson3Completed", false)

            //assuming there are three lessons

            if (lesson2Completed && lesson3Completed) {
                editor.putBoolean("Lesson4Completed", true) // automatically make next lesson clickable
            }
            editor.apply()

            // start the specific activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}