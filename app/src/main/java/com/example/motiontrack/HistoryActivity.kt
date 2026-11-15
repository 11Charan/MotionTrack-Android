package com.example.motiontrack

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val sessions = mutableListOf<Session>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        recyclerView = findViewById(R.id.recyclerView)
        val clearButton = findViewById<Button>(R.id.clearButton)
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadSessions()
        clearButton.setOnClickListener {
            clearHistory()
        }
    }

    private fun loadSessions() {
        sessions.clear()
        val preferences = getSharedPreferences("motion_sessions", MODE_PRIVATE)
        val storedSessions = preferences.getStringSet("sessions", emptySet())
        storedSessions?.forEach { value ->
            val parts = value.split("|")
            if (parts.size == 3) {
                val session =
                    Session(
                        parts[0],
                        parts[1].toInt(),
                        parts[2].toLong()
                    )
                sessions.add(session)
            }
        }

        sessions.sortByDescending {
            it.date
        }

        recyclerView.adapter = SessionAdapter(sessions)
    }

    private fun clearHistory() {
        val preferences =
            getSharedPreferences("motion_sessions", MODE_PRIVATE)

        preferences.edit().clear().apply()

        sessions.clear()

        recyclerView.adapter =
            SessionAdapter(sessions)
    }
}