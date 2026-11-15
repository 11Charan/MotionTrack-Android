package com.example.motiontrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sessionNameEditText: EditText
    private lateinit var lastScoreText: TextView
    private val sensorActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == RESULT_OK) {
                val score = result.data?.getFloatExtra("score", 0f) ?: 0f
                lastScoreText.text = "Last movement score: ${score.toInt()}"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionNameEditText = findViewById(R.id.sessionNameEditText)
        lastScoreText = findViewById(R.id.lastScoreText)

        val startButton = findViewById<Button>(R.id.startButton)
        val historyButton = findViewById<Button>(R.id.historyButton)

        startButton.setOnClickListener {
            var sessionName = sessionNameEditText.text.toString()
            if (sessionName.isBlank()) {
                sessionName = "Motion Session"
            }
            val intent = Intent(this, SensorActivity::class.java)
            intent.putExtra("sessionName", sessionName)
            sensorActivityLauncher.launch(intent)
        }

        historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}