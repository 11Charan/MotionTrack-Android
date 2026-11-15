
# MotionTrack — Android Motion Tracking App

An Android application built with **Kotlin** for monitoring and visualizing device motion using the **accelerometer** and **gyroscope**.

Developed as a Mobile Systems final project at Schmalkalden University of Applied Sciences.

## Features

- Create named motion-tracking sessions.
- View live accelerometer and gyroscope readings on the X, Y, and Z axes.
- Calculate and display a movement score on a **0–100** scale.
- Visualize accelerometer movement with a custom `MotionView` drawn on an Android `Canvas`.
- Pause or resume sensor tracking using a switch.
- Save completed sessions locally and browse their scores and timestamps in a `RecyclerView` history screen.

## Screens and Flow

### 1. Main Screen (`MainActivity`)

- Enter a session name.
- Start a motion-tracking session.
- View the latest movement score.
- Access previously recorded sessions.

### 2. Sensor Screen (`SensorActivity`)

- Monitor live accelerometer and gyroscope values.
- Visualize movement through a custom graphical view.
- Display the calculated movement score.
- Enable or disable sensor tracking.
- Finish and save the session.

### 3. History Screen (`HistoryActivity`)

- View previously completed tracking sessions.
- Display session names, movement scores, and timestamps.

The application passes data between activities using **Intents** and the **Activity Result API** and stores session history using **SharedPreferences**.

## Technology Stack

| Component | Technology |
|-----------|------------|
| Language | Kotlin |
| Platform | Android |
| Minimum SDK | API 34 (Android 14) |
| Compile / Target SDK | API 37 |
| UI | XML Layouts, Android Views |
| Sensors | Accelerometer, Gyroscope |
| Visualization | Android Canvas |
| Session History | RecyclerView |
| Local Storage | SharedPreferences |
| Build System | Gradle Kotlin DSL |
| IDE | Android Studio |

## Installation and Setup

### Prerequisites

- Android Studio
- Android SDK
- Android device or emulator running Android 14 or newer
- Git

### Getting Started

**1. Clone the repository**

```bash
git clone https://github.com/11Charan/MotionTrack-Android.git
```

**2. Open the project**

- Launch Android Studio.
- Select **Open**.
- Navigate to the cloned project directory.
- Wait for Gradle synchronization to complete.

**3. Run the application**

- Connect an Android device or start an emulator.
- Select the `app` run configuration.
- Click **Run**.

**4. Start tracking**

- Enter a session name.
- Select **Start Motion Test**.
- Move the device to observe sensor readings.
- Finish the session to save the results.
- Open Session History to review previous sessions.

## Project Structure

```text
MotionTrack-Android/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/motiontrack/
│           │       ├── MainActivity.kt
│           │       ├── SensorActivity.kt
│           │       ├── HistoryActivity.kt
│           │       ├── MotionView.kt
│           │       ├── Session.kt
│           │       └── SessionAdapter.kt
│           ├── res/
│           └── AndroidManifest.xml
├── docs/
│   └── MotionTrack-Project-Report.pdf
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Testing

The application was tested using a **Pixel 7 emulator running Android 14 (API 34)**.

Android Emulator's virtual sensor controls were used to simulate device movement and rotation.

The movement score was scaled to a range of 0–100 to make simulated motion easier to visualize.

Sensor availability and readings may vary across devices and emulators.

## Project Documentation

The complete project report contains:

- Project description and objectives
- Application features
- Main screen screenshots
- Sensor tracking and motion visualization
- Session history implementation
- Technical details and testing notes

**[View Complete Project Report (PDF)](docs/MotionTrack-Project-Report.pdf)**

## Notes

- The movement score is an application-specific visualization metric, not a calibrated physical measurement.
- Generated build files, IDE caches, and machine-specific configurations should be excluded from version control.

## Author

**Sricharan Sureshkumar**

Mobile Systems

Schmalkalden University of Applied Sciences, Germany

GitHub: [@11Charan](https://github.com/11Charan)
