package com.example.test_four

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class DatabaseHelper private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val databasePath = context.getDatabasePath(DATABASE_NAME).path

    companion object {
        private const val DATABASE_NAME = "Dictionary.db"
        private const val DATABASE_VERSION = 2

        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(context.applicationContext)
                instance!!.copyDatabaseFromAssets(context)
            }
            return instance!!
        }
    }

    private fun copyDatabaseFromAssets(context: Context) {
        try {
            val dbFile = File(databasePath)
            if (dbFile.exists()) {
                // Delete the old database if it exists
                dbFile.delete()
                Log.d("DatabaseHelper", "Old database deleted.")
            }
            // Proceed with copying the new database
            val inputStream: InputStream = context.assets.open(DATABASE_NAME)
            val outputStream: OutputStream = FileOutputStream(dbFile)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()
            Log.d("DatabaseHelper", "Database copied successfully to internal storage.")
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error copying database from assets: ${e.message}")
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // No need to create the table here since we are using a pre-built database
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade as needed
    }

    fun getWordsForLanguage(language: String): List<Pair<String, String>> {
        val words = mutableListOf<Pair<String, String>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT word, translation FROM Dictionary_database WHERE language = ?", arrayOf(language))

        if (cursor.moveToFirst()) {
            do {
                val word = cursor.getString(cursor.getColumnIndexOrThrow("word"))
                val translation = cursor.getString(cursor.getColumnIndexOrThrow("translation"))
                words.add(Pair(word, translation))
            } while (cursor.moveToNext())
        } else {
            Log.e("DatabaseHelper", "No words found for language: $language")
        }
        cursor.close()
        return words
    }
}