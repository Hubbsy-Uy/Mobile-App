package com.example.test_four

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import android.view.View

class Dictionary : AppCompatActivity() {

    private lateinit var textViewWords: TextView
    private lateinit var spinnerLanguages: Spinner
    private lateinit var editTextSearch: EditText
    private lateinit var buttonSearch: Button

    private val dictionary = mutableMapOf<String, MutableMap<String, String>>()
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)

        title = "Dictionary"

        textViewWords = findViewById(R.id.textViewWords)
        spinnerLanguages = findViewById(R.id.spinnerLanguage)
        editTextSearch = findViewById(R.id.editTextSearch)
        buttonSearch = findViewById(R.id.buttonSearch)

        dbHelper = DatabaseHelper.getInstance(this)

        // Load words from the database and set up the spinner
        loadWordsFromDatabase()
    }

    private fun loadWordsFromDatabase() {
        try {
            val db = dbHelper.readableDatabase
            Log.d("Dictionary", "Database opened successfully.")
            Log.d("DatabaseHelper", "Current Database Version: ${db.version}")

            dictionary.clear() // Clear existing data

            if (db.isOpen) {
                val cursor = db.rawQuery("SELECT * FROM Dictionary_database", null)

                if (cursor.moveToFirst()) {
                    do {
                        val language = cursor.getString(cursor.getColumnIndexOrThrow("language"))
                        val word = cursor.getString(cursor.getColumnIndexOrThrow("word"))
                        val translation = cursor.getString(cursor.getColumnIndexOrThrow("translation"))

                        if (!language.isNullOrEmpty() && !word.isNullOrEmpty() && !translation.isNullOrEmpty()) {
                            if (language !in listOf("English", "Spanish")) {
                                dictionary.getOrPut(language) { mutableMapOf() }[word] = translation
                                Log.d("Dictionary", "Loaded: Language = $language, Word = $word, Translation = $translation")
                            }
                        }
                    } while (cursor.moveToNext())
                } else {
                    Log.e("Dictionary", "No data found in the table.")
                }

                cursor.close()
            } else {
                Log.e("Dictionary", "Database is not open.")
            }

            db.close()
            Log.d("Dictionary", "Database closed.")
        } catch (e: Exception) {
            Log.e("Dictionary", "Error loading words: ${e.message}")
            Toast.makeText(this, "Error loading words: ${e.message}", Toast.LENGTH_LONG).show()
        }

        if (dictionary.isEmpty()) {
            Log.e("Dictionary", "Dictionary map is empty.")
        } else {
            Log.d("Dictionary", "Languages loaded: ${dictionary.keys}")
            setupSpinner()
        }
    }

    private fun setupSpinner() {
        val languages = dictionary.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguages.adapter = adapter

        spinnerLanguages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent.getItemAtPosition(position) as String
                Log.d("Dictionary", "Selected Language: $selectedLanguage")
                displayWordsForLanguage(selectedLanguage)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun displayWordsForLanguage(language: String) {
        val words = dictionary[language] ?: emptyMap()
        val stringBuilder = StringBuilder()

        if (words.isEmpty()) {
            stringBuilder.append("No words found for $language.")
        } else {
            for ((word, translation) in words) {
                val capitalizedWord = word.replaceFirstChar { it.titlecase(Locale.getDefault()) }
                val capitalizedTranslation = translation.replaceFirstChar { it.titlecase(Locale.getDefault()) }
                stringBuilder.append("<b>$capitalizedWord</b>: $capitalizedTranslation<br>")
            }
        }

        textViewWords.text = Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_COMPACT)
        Log.d("Dictionary", "Displayed words for $language.")
    }
}