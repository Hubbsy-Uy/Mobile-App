package com.example.test_four


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import android.widget.AdapterView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class Tagalog : AppCompatActivity() {

    lateinit var content: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tagalog)


        val spinnerLanguages: Spinner = findViewById(R.id.languageSpinner)
        val languages = arrayOf("Ilocano", "Tagalog", "Cebuano", "Hiligaynon", "Kapampangan", "Ibaloi", "Kalanguya")
        val intent1 = Intent(this, MainActivity::class.java)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguages.adapter = spinnerAdapter
        spinnerLanguages.setSelection(1) // Default selection to the first item

        // Set the OnItemSelectedListener for the spinner
        spinnerLanguages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languages[position]
                if (selectedLanguage == "Hiligaynon") {
                    // Show toast message when "Hiligaynon" is selected
                    showToast("Sorry, we don't have a budget to create this language.", Toast.LENGTH_SHORT)
                }
                if (selectedLanguage == "Ilocano") {
                    startActivity(intent1)
                }
                if (selectedLanguage == "Cebuano") {
                    // Show toast message when "Hiligaynon" is selected
                    showToast("Sorry, we don't have a budget to create this language.", Toast.LENGTH_SHORT)
                }
                if (selectedLanguage == "Kapampangan") {
                    // Show toast message when "Hiligaynon" is selected
                    showToast("Sorry, we don't have a budget to create this language.", Toast.LENGTH_SHORT)
                }
                if (selectedLanguage == "Ibaloi") {
                    // Show toast message when "Hiligaynon" is selected
                    showToast("Sorry, we don't have a budget to create this language.", Toast.LENGTH_SHORT)
                }
                if (selectedLanguage == "Kalanguya") {
                    // Show toast message when "Hiligaynon" is selected
                    showToast("Sorry, we don't have a budget to create this language.", Toast.LENGTH_SHORT)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Handle the case where no item is selected, if needed
            }
        }


        // Handling window insets for modern devices
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setupButtonListeners()
    }



    private fun setupButtonListeners() {
        // Dictionary button
        val dictionaryButton: Button = findViewById(R.id.dictionaryButton)
        dictionaryButton.setOnClickListener {
            val intent = Intent(this, Dictionary::class.java)
            startActivity(intent)
            showToast("Dictionary loaded! Start exploring new words.", Toast.LENGTH_SHORT)
        }

        // Exercise button
        val exerciseButton: Button = findViewById(R.id.exercise)
        exerciseButton.setOnClickListener {
            val intent = Intent(this, exercise::class.java)
            startActivity(intent)
            showToast("Ready for a challenge? Start your exercises now!", Toast.LENGTH_SHORT)
        }
        val taga: ImageButton = findViewById(R.id.tagalog)
        taga.setOnClickListener {
            val intent = Intent(this, Taga1::class.java)
            startActivity(intent)

        }

        // Chat button
        val chatButton: Button = findViewById(R.id.chat)
        chatButton.setOnClickListener {
            val intent = Intent(this, chatty::class.java)
            startActivity(intent)
        }

        // Profile button
        val profileButton: Button = findViewById(R.id.profile)
        profileButton.setOnClickListener {
            val intent = Intent(this, profile1::class.java)
            startActivity(intent)
            showToast("Profile updated successfully! Check out your latest info.", Toast.LENGTH_SHORT)
        }
    }

    private fun showToast(message: CharSequence, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }


}