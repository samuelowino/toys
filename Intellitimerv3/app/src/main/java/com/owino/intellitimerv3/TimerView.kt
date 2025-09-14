package com.owino.intellitimerv3
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import com.google.android.material.button.MaterialButton
class TimerView: AppCompatActivity() {
    companion object {
        const val RECEIVER_TAG = "TimerView.BroadcastReceiver"
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 333112
        const val DURATION_UPDATE_KEY = "TimerView.BroadcastReceiver.duration_update"
        const val DURATION_UPDATE_BROADCAST_INTENT = "TimerView.BroadcastReceiver.intent_filter"
        const val DURATION_FINISHED_KEY = "TimerView.BroadcastReceiver.duration_finished"
        const val TIMER_UPDATE_INTENT_KEY = "TimerView.update_pending_intent_extra"
    }
    private lateinit var timerToggleButton: MaterialButton
    private lateinit var timerTextView: TextView
    private lateinit var sessionLabelEditText: AppCompatEditText
    private var isFinished = false
    private var tickTickReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e(RECEIVER_TAG,"on Receive")
            if (intent == null || context == null) return
            if (intent.action != DURATION_UPDATE_BROADCAST_INTENT) return
            val durationLabel:String? = intent.getStringExtra(DURATION_UPDATE_KEY)
            val timerFinished: Boolean = intent.getBooleanExtra(DURATION_FINISHED_KEY,false)
            if (durationLabel != null)
                timerTextView.text = durationLabel
            if (timerFinished){
                sessionLabelEditText.isEnabled = true
                isFinished = true
                Toast.makeText(applicationContext,"Finished 🏁", Toast.LENGTH_LONG).show()
            } else {
                isFinished = false
                timerToggleButton.visibility = View.GONE
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer_view_layout)
        timerTextView = findViewById(R.id.timer_text_view)
        timerToggleButton = findViewById(R.id.timer_action_button)
        sessionLabelEditText = findViewById(R.id.session_label_edit_text)
        timerToggleButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                val granted = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                if (granted != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        NOTIFICATION_PERMISSION_REQUEST_CODE)
                } else {
                    startTimer()
                }
            } else {
                startTimer()
            }
        }
    }
    @SuppressLint("MutableImplicitPendingIntent")
    private fun startTimer(){
        var sessionLabel: String = sessionLabelEditText.text.toString()
        if (sessionLabel.isEmpty())
            sessionLabel = getString(R.string.general_default_session)
        val tickTickIntent = Intent(applicationContext,TickTickService::class.java)
        tickTickIntent.putExtra(TickTickService.SESSION_LABEL_KEY,sessionLabel)
        val durationMillis: Long = 35 * 1_000
        tickTickIntent.putExtra(TickTickService.TIMER_DURATION_KEY,durationMillis)
        val timeUpdateIntent = Intent(DURATION_UPDATE_BROADCAST_INTENT)
        val timerUpdatePendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            PendingIntent.getBroadcast(
            applicationContext,
            0,
            timeUpdateIntent,
                PendingIntent.FLAG_ALLOW_UNSAFE_IMPLICIT_INTENT or
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            ) else PendingIntent.getBroadcast(
            applicationContext,
            0,
            timeUpdateIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        tickTickIntent.putExtra(TIMER_UPDATE_INTENT_KEY,timerUpdatePendingIntent)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(tickTickIntent)
        } else
            startService(tickTickIntent)
        if (TickTickService.isRunning)
            timerToggleButton.text = getString(R.string.general_stop)
        sessionLabelEditText.isEnabled = false
    }
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val granted = checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
            if (granted != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE)
            }
        }
        if (TickTickService.isRunning){
            timerToggleButton.text = getString(R.string.general_stop)
        } else {
            timerToggleButton.text = getString(R.string.general_start)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            registerReceiver(tickTickReceiver, IntentFilter(DURATION_UPDATE_BROADCAST_INTENT),
                RECEIVER_EXPORTED
            )
        } else {
            registerReceiver(tickTickReceiver, IntentFilter(DURATION_UPDATE_BROADCAST_INTENT))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(tickTickReceiver)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE &&
            grantResults.contains(PackageManager.PERMISSION_GRANTED)){
            startTimer()
        }
    }
}