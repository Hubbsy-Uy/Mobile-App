// first page or greeting page of the lesson

package com.example.test_four

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class FirstPage : AppCompatActivity() {

    private lateinit var speechTexts: Array<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        // retrieve the lesson title, content, and greeting
        val lessonTitle = intent.getStringExtra("LESSON_TITLE")
        val lessonContent = intent.getStringExtra("LESSON_CONTENT")
        val lessonGreeting = intent.getStringExtra("LESSON_GREETING")
        //retrieve lesson data
        val lessonIndex = intent.getIntExtra("LESSON_INDEX",0)
        speechTexts = intent.getStringArrayExtra("SPEECH_TEXTS") ?: arrayOf()

        // Set the text in the TextViews
        findViewById<TextView>(R.id.textView).text = lessonTitle ?: "Default Lesson Title"
        findViewById<TextView>(R.id.textView2).text = lessonGreeting ?: "Default Greeting"
        // Display the greeting (you may want to add another TextView for this)
        findViewById<TextView>(R.id.textView3).text = lessonContent ?: "Default Content"
        // Set up the back button
        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish() // Closes the current activity and returns to the previous one
        }        // At the end of this activity, when the user finishes the lesson:
        // (e.g., navigating to ThirdPage)
        findViewById<Button>(R.id.contButton).setOnClickListener {
            val sharedPreferences = getSharedPreferences("LessonPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(
                "Lesson${lessonIndex + 1}Completed",
                true
            ) // Mark next lesson as completed
            editor.apply()
        }
        val goToSpeakButton: Button = findViewById(R.id.contButton)
        goToSpeakButton.setOnClickListener{
            val intent = Intent(this, SpeakActivity::class.java)
            intent.putExtra("LESSON_SPEECH", speechTexts[lessonIndex])
            startActivity(intent)
        }

    }
}

