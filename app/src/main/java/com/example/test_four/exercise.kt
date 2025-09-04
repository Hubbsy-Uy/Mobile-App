package com.example.test_four
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.test_four.R

class exercise : ComponentActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // Access each button by ID
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)
        val button5 = findViewById<Button>(R.id.button5)
        val button6 = findViewById<Button>(R.id.button6)
        val button11 = findViewById<Button>(R.id.button11)
        val home = findViewById<Button>(R.id.home)
        val prof = findViewById<Button>(R.id.profile)

        // Set click listener for button2 to redirect to another activity
        button3.setOnClickListener {
            val intent = Intent(this, flashcards::class.java) // Replace with the target activity class
            startActivity(intent)
        }
        home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java) // Replace with the target activity class
            startActivity(intent)
        }
        prof.setOnClickListener {
            val intent = Intent(this, profile1::class.java) // Replace with the target activity class
            startActivity(intent)
        }

        // Assign the click listener to other buttons to show the dialog
        val buttons = listOf(button2, button4, button5, button6, button11)
        buttons.forEach { button ->
            button.setOnClickListener {
                showComingSoonDialog()
            }
        }
    }

    private fun showComingSoonDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Coming Soon")
            setMessage("This feature is under development.")
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss() // Close the dialog when "OK" is clicked
            }
        }.show()
    }
}