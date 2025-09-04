package com.example.test_four

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class Taga3 : AppCompatActivity(), OnInitListener {

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taga3)

        // Handle window insets for modern devices (for system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(this, this)

        // Set up button listeners
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        // Back button
        val dictionaryButton: Button = findViewById(R.id.ans2)
        dictionaryButton.setOnClickListener {
            val intent = Intent(this, Tagalog::class.java) // Navigate to Tagalog Activity
            startActivity(intent)
        }

        // Continue button
        val exerciseButton: Button = findViewById(R.id.ans1)
        exerciseButton.setOnClickListener {

            speakText("Kamusta")

        }
    }

    // TTS initialization callback
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language to Filipino (Tagalog)
            val langResult = textToSpeech.setLanguage(Locale("tl", "PH"))
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported or missing data")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }

    // Function to speak the given text
    private fun speakText(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        // Don't forget to shutdown the TTS engine
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
