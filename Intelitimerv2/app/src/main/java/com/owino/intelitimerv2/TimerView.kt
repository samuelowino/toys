package com.owino.intelitimerv2
import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.owino.intelitimerv2.TimeTickTickService.Companion.TIMER_EXTERNAL_UPDATE_KEY
class TimerView: AppCompatActivity() {
    companion object {
        const val TAG = "TimerView"
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 44455
        const val TIMER_UPDATES_INTENTS_KEY = "com.owino.intelitimerv2.updates_intent"
        const val TIMER_BROADCAST_REQUEST_CODE = 7811
    }
    private lateinit var timerTextView: TextView
    private lateinit var startButton: MaterialButton
    private lateinit var containerView: View
    private var timerUpdatesReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e(TAG,"on Receive timer updates")
            if (intent == null) return
            if (intent.action == TIMER_UPDATES_INTENTS_KEY){
                Log.e(TAG,"Captured intent action correctly")
            }
            val durationLabel = intent.getStringExtra(TIMER_EXTERNAL_UPDATE_KEY)
            Log.e(TAG,"Duration label $durationLabel")
            if (durationLabel == null) return
            timerTextView.text = durationLabel
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_layout)
        timerTextView = findViewById(R.id.timer_text_view)
        startButton = findViewById(R.id.start_timer_button)
        containerView = findViewById(R.id.container_view)
        startButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val notificationPermissionGranted = ActivityCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS)
                if (notificationPermissionGranted != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(
                        containerView,
                        "Missing notification Permissions",
                        Snackbar.LENGTH_LONG
                    )
                        .setAction("Grant Permission", object : View.OnClickListener {
                            override fun onClick(v: View?) {
                                updateNotificationPermissions()
                            }
                        })
                        .show()
                    return@setOnClickListener
                } else {
                    startTimer()
                }
            } else {
                startTimer()
            }
        }
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun startTimer() {
        val timerIntent = Intent(applicationContext, TimeTickTickService::class.java)
        val _10Seconds = 10 * 1_000
        timerIntent.putExtra(TimeTickTickService.TIMER_DURATION_MILLIS,_10Seconds)
        timerIntent.putExtra(TimeTickTickService.TIMER_SESSION_LABEL_KEY,"A Task ♥️")
        val timerUpdatesIntent = Intent(TIMER_UPDATES_INTENTS_KEY)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                timerUpdatesReceiver,
                IntentFilter(TIMER_UPDATES_INTENTS_KEY),
                RECEIVER_EXPORTED
            )
        } else {
            registerReceiver(
                timerUpdatesReceiver,
                IntentFilter(TIMER_UPDATES_INTENTS_KEY))
        }
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            TIMER_BROADCAST_REQUEST_CODE,
            timerUpdatesIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT or
                    PendingIntent.FLAG_MUTABLE
        )
        timerIntent.putExtra(TimeTickTickService.TIMER_UPDATE_PENDING_INTENT,pendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(timerIntent)
        } else {
            startService(timerIntent)
        }
    }
    override fun onStart() {
        super.onStart()
        updateNotificationPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timerUpdatesReceiver)
    }
    private fun updateNotificationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermissionGranted = ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (notificationPermissionGranted != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }
}