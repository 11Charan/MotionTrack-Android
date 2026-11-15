package com.example.motiontrack

import android.app.Activity
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class SensorActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var gyroscope: Sensor? = null
    private lateinit var accelerometerText: TextView
    private lateinit var gyroscopeText: TextView
    private lateinit var scoreText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var trackingSwitch: Switch
    private lateinit var motionView: MotionView
    private var sessionName = ""
    private var movementScore = 0f
    private var measurementCount = 0
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var firstReading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sensor)
        sessionName = intent.getStringExtra("sessionName") ?: "Motion Session"
        val sessionTitle = findViewById<TextView>(R.id.sessionTitle)
        sessionTitle.text = sessionName
        accelerometerText = findViewById(R.id.accelerometerText)
        gyroscopeText = findViewById(R.id.gyroscopeText)
        scoreText = findViewById(R.id.scoreText)
        progressBar = findViewById(R.id.progressBar)
        trackingSwitch = findViewById(R.id.trackingSwitch)
        motionView = findViewById(R.id.motionView)
        val finishButton = findViewById<Button>(R.id.finishButton)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        // Sensor 1
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        // Sensor 2
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        trackingSwitch.isChecked = true
        finishButton.setOnClickListener {
            finishSession()
        }
    }

    override fun onResume() {
        super.onResume()
        // Start accelerometer
        accelerometer?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }

        // Start gyroscope
        gyroscope?.let {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) { return }
        if (!trackingSwitch.isChecked) { return }

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            accelerometerText.text = "X: %.2f\nY: %.2f\nZ: %.2f".format(x, y, z)

            if (firstReading) {
                lastX = x
                lastY = y
                lastZ = z
                firstReading = false
                motionView.updateMovement(x, y)
                return
            }

            val deltaX = abs(x - lastX)
            val deltaY = abs(y - lastY)
            val deltaZ = abs(z - lastZ)

            val movement = deltaX + deltaY + deltaZ

            lastX = x
            lastY = y
            lastZ = z

            movementScore += movement
            measurementCount++

            val average = movementScore / measurementCount
            val displayScore = (average * 30).toInt().coerceIn(0, 100)
            scoreText.text = "Movement score: $displayScore"
            progressBar.max = 100
            progressBar.progress = displayScore
            motionView.updateMovement(x, y)
        }

        if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            gyroscopeText.text = "X: %.2f\nY: %.2f\nZ: %.2f".format(x, y, z)
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {
        // Nothing needed here
    }
    private fun finishSession() {
        var finalScore = 0f
        if (measurementCount > 0) {
            val average = movementScore / measurementCount
            finalScore = (average * 30).coerceIn(0f, 100f)
        }

        saveSession(finalScore)

        val resultIntent = Intent()
        resultIntent.putExtra(
            "score",
            finalScore
        )

        setResult(
            Activity.RESULT_OK,
            resultIntent
        )

        finish()
    }

    private fun saveSession(score: Float) {
        val preferences = getSharedPreferences("motion_sessions", MODE_PRIVATE)
        val sessions = preferences.getStringSet("sessions", emptySet())?.toMutableSet() ?: mutableSetOf()
        val session = "$sessionName|${score.toInt()}|${System.currentTimeMillis()}"

        sessions.add(session)

        preferences.edit()
            .putStringSet(
                "sessions",
                sessions
            )
            .apply()
    }
}