//second page which is the quiz and text to speech feature

package com.example.test_four

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class SpeakActivity : AppCompatActivity() {

    private lateinit var tts: TextToSpeech

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.speech_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        finds a button with the id "rightanswer1" that when clicked, creates an instance of rightanswer that is being displayed.
        val rightans: Button = findViewById(R.id.rightanswer1)
        rightans.setOnClickListener {
            val showPopUp = rightanswer() // Assuming rightanswer is a DialogFragment
            showPopUp.show(supportFragmentManager, "showPopUp")
        }

        //finds a button with the id "wrongans" that when clicked, a wrong ans instance is displayed
        val wrongans: Button = findViewById(R.id.wrongans1)
        wrongans.setOnClickListener {
            val showPopUp = wrongans() // Assuming wrongans is a DialogFragment
            showPopUp.show(supportFragmentManager, "showPopUp")
        }

        val wrongans1: Button = findViewById(R.id.wrongans2)
        wrongans1.setOnClickListener {
            val showPopUp = wrongans() // Assuming wrongans is a DialogFragment
            showPopUp.show(supportFragmentManager, "showPopUp")
        }

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts.setLanguage(Locale("fil", "PH")) // use filipino as a fallback
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // handle the error
                }
            }
        }

        val lessonSpeech = intent.getStringExtra("LESSON_SPEECH") ?: "Default speech text"
        val speechTextView: TextView = findViewById(R.id.textView4)
        speechTextView.text = lessonSpeech

        // set up the back button
        findViewById<Button>(R.id.back_button2).setOnClickListener {
            finish() // closes the current activity and returns to the previous one
        }

        // button to trigger speech
        val speakButton: ImageButton = findViewById(R.id.button_speak)
        speakButton.setOnClickListener {
            speak(lessonSpeech)
        }

        findViewById<Button>(R.id.back_button2).setOnClickListener {
            finish()
        }
    }

    private fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}







