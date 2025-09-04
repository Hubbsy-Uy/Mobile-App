package com.example.test_four

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_four.Adapter.HorizontalRecyclerView
import com.google.android.material.button.MaterialButton
import java.util.*

const val CHANNEL_ID = "channelId"
const val NOTIFICATION_REQUEST_CODE = 1001
const val PREFS_NAME = "AppPrefs"
const val NOTIFICATION_SHOWN_KEY = "notification_shown"

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var adapter: HorizontalRecyclerView // Declare this globally if you want to update the RecyclerView
    private lateinit var newArrayList: ArrayList<Course>
    private lateinit var tts: TextToSpeech
    private val connectivityReceiver = ConnectivityReceiver()
    lateinit var heading: Array<String>
    lateinit var content: Array<String>
    lateinit var greetings: Array<String>
    lateinit var speechTexts: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the Spinner for languages
        val spinnerLanguages: Spinner = findViewById(R.id.languageSpinner)
        val languages = arrayOf("Ilocano", "Tagalog", "Cebuano", "Hiligaynon", "Kapampangan", "Ibaloi", "Kalanguya")
        val intent = Intent(this, Tagalog::class.java)  // 'this' refers to MainActivity, 'Tagalog::class.java' is the target activity

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerLanguages.adapter = spinnerAdapter
        spinnerLanguages.setSelection(0) // Default selection to the first item

        // Set the OnItemSelectedListener for the spinner
        spinnerLanguages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languages[position]
                if (selectedLanguage == "Hiligaynon") {
                    // Show toast message when "Hiligaynon" is selected
                    showToast("Sorry, we don't have a budget to create this language.", Toast.LENGTH_SHORT)
                }
                if (selectedLanguage == "Tagalog") {
                    startActivity(intent)
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

        // Ensure the notification channel is created before showing notifications
        createNotificationChannel()

        // Handling window insets for modern devices
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Show notification only if the app is launched for the first time in the current session
        showNotificationOnceOnAppLaunch()

        // Initialize TextToSpeech
        tts = TextToSpeech(this, this)

        // Initialize arrays with content for the recycler view
        heading = arrayOf("Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4", "Lesson 5")
        greetings = arrayOf(
            getString(R.string.greet_a),
            getString(R.string.greet_b),
            getString(R.string.greet_c),
            getString(R.string.greet_d),
            getString(R.string.greet_e)
        )
        content = arrayOf(
            getString(R.string.first),
            getString(R.string.second),
            getString(R.string.third),
            getString(R.string.fourth),
            getString(R.string.fifth)
        )
        speechTexts = arrayOf(
            "Naimbag nga bigat",
            "Anya ti nagan mo?",
            "Ayanna ti balay ni Aling Maria?",
            "Sagmamano daytoy/dayta",
            "Mangan tayon!"
        )

        // Set up RecyclerView
        newArrayList = arrayListOf()
        newRecyclerView = findViewById(R.id.recyclerView)

        // Initialize the RecyclerView adapter
        adapter = HorizontalRecyclerView(newArrayList, greetings)
        newRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        newRecyclerView.adapter = adapter

        getUserdata()

        // Set up buttons with intents
        setupButtonListeners()
    }

    private fun getUserdata() {
        for (i in heading.indices) {
            val course = Course(heading[i], content[i])
            newArrayList.add(course)
        }
        adapter.notifyDataSetChanged()
    }

    private fun showNotificationOnceOnAppLaunch() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val hasNotificationShown = sharedPreferences.getBoolean(NOTIFICATION_SHOWN_KEY, false)

        // Show notification only if it has not been shown yet
        if (!hasNotificationShown) {
            setupDynamicNotification()

            // Mark notification as shown
            sharedPreferences.edit().putBoolean(NOTIFICATION_SHOWN_KEY, true).apply()
        }
    }

    private fun setupDynamicNotification() {
        val titles = arrayOf(
            "Word of the Day!",
            "Philippine Language Fun Fact!",
            "Discover a New Word!",
            "Did You Know?",
            "Explore Philippine Languages!"
        )

        val descriptions = arrayOf(
            "Today's word is 'Magandang umaga!' — it means 'Good morning!' in Tagalog.",
            "Fun Fact: Did you know that the Philippines has over 180 languages? Tagalog, Cebuano, and Ilocano are some of the most widely spoken!",
            "Word of the Day: 'Kumusta' — It means 'How are you?' in both Cebuano and Tagalog, derived from the Spanish word '¿Cómo está?'",
            "Fun Fact: The Ibanag language, spoken in Northern Luzon, is one of the oldest in the Philippines and still actively used today.",
            "Learn: 'Mangan' — meaning 'Let's eat!' in Ilocano. It's one of the many regional languages of the Philippines!"
        )

        // Randomly select a title and description
        val randomIndex = (titles.indices).random()
        val selectedTitle = titles[randomIndex]
        val selectedDescription = descriptions[randomIndex]

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(selectedTitle)
            .setContentText(selectedDescription)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Check for notification permission on Android 13+
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_REQUEST_CODE
            )
        } else {
            with(NotificationManagerCompat.from(this)) {
                notify(1, builder.build())
            }
        }
    }

    private fun createNotificationChannel() {
        // Notification channels are required on Android 8.0 and above (API level 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "First Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Test description for our app"
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == NOTIFICATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, show the notification
                setupDynamicNotification()
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set language for TTS
            val langResult = tts.setLanguage(Locale.getDefault())
            if (langResult == TextToSpeech.LANG_MISSING_DATA ||
                langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                showToast("Language is not supported or missing data.", Toast.LENGTH_LONG)
            }
        } else {
            showToast("TTS initialization failed.", Toast.LENGTH_SHORT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }
}
