package com.example.test_four.Adapter

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test_four.Course
import com.example.test_four.R
import com.example.test_four.FirstPage
import com.example.test_four.MainActivity

class HorizontalRecyclerView(private val courseList: ArrayList<Course>, private val greetings: Array<String>) : RecyclerView.Adapter<HorizontalRecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvHeading)
        val startButton: Button = itemView.findViewById(R.id.startButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.course_num1, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = courseList[position]
        holder.title.text = currentItem.heading

        // get SharedPreferences to check lesson completion
        val sharedPreferences = holder.itemView.context.getSharedPreferences("LessonPrefs", MODE_PRIVATE)
        val isPreviousLessonCompleted = position == 0 || sharedPreferences.getBoolean("Lesson${position}Completed", false)

        // Set button state and colors based on lesson completion
        holder.startButton.isEnabled = isPreviousLessonCompleted

        // Change the button color based on the enabled state
        if (isPreviousLessonCompleted) {
            holder.startButton.setBackgroundColor(Color.parseColor("#A8B876")) // Enabled color
            holder.startButton.setTextColor(Color.parseColor("#606C38")) // Text color for enabled
        } else {
            holder.startButton.setBackgroundColor(Color.parseColor("#D3D3D3")) // Disabled color (e.g., light gray)
            holder.startButton.setTextColor(Color.parseColor("#A9A9A9")) // Text color for disabled (e.g., dark gray)
        }

        // set an OnClickListener for the button
        holder.startButton.setOnClickListener {
            if (isPreviousLessonCompleted) {
                val context = holder.itemView.context
                val intent = Intent(context, FirstPage::class.java)
                intent.putExtra("LESSON_TITLE", currentItem.heading) // Pass the title
                intent.putExtra("LESSON_CONTENT", currentItem.content) // Pass the content
                intent.putExtra("LESSON_GREETING", greetings[position]) // Pass the corresponding greeting
                intent.putExtra("SPEECH_TEXTS", (context as MainActivity).speechTexts)
                intent.putExtra("LESSON_INDEX", position)
                intent.putExtra("TEXT_VIEW", currentItem.heading)
                context.startActivity(intent)

                // mark current lesson as completed when starting the next lesson
                val editor = sharedPreferences.edit()
                editor.putBoolean("Lesson${position + 1}Completed", true) // mark next lesson as completed
                editor.apply()
            }
        }
    }
    override fun getItemCount(): Int {
        return courseList.size
    }
}
//        findViewById<TextView>(R.id.textView).text = lessonTitle ?: "Default Lesson Title"