package com.owino.intellitimerv3
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.owino.intellitimerv3.TimerView.Companion.DURATION_FINISHED_KEY
import com.owino.intellitimerv3.TimerView.Companion.DURATION_UPDATE_BROADCAST_INTENT
import com.owino.intellitimerv3.TimerView.Companion.DURATION_UPDATE_KEY
import com.owino.intellitimerv3.TimerView.Companion.TIMER_UPDATE_INTENT_KEY
import java.util.Date
import java.util.Locale
class TickTickService: Service() {
    private lateinit var startTime: Date
    private lateinit var sessionLabel: String
    companion object {
        const val TIMER_DURATION_KEY = "com.owino.intellitimerv3.duration"
        const val NOTIFICATION_CHANNEL_ID = "com.owino.intellitimerv3.notification_channel_id"
        const val SESSION_LABEL_KEY = "com.owino.intellitimerv3.session_label"
        const val ACTIVITY_LAUNCH_INTENT = 90011
        const val NOTIFICATION_ID = 304561
        const val TAG = "TickTickService"
        @Volatile
        var isRunning = false
            private set
    }
    override fun onCreate() {
        super.onCreate()
        startTime = Date()
        isRunning = true
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) return START_NOT_STICKY
        val updatePendingIntent: PendingIntent? = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra(
            TIMER_UPDATE_INTENT_KEY,
            PendingIntent::class.java)
        else
            intent.getParcelableExtra(TIMER_UPDATE_INTENT_KEY)
        if (updatePendingIntent == null) {
            Log.e(TAG,"Error missing updates pending intent")
            return START_NOT_STICKY
        }
        sessionLabel = intent.getStringExtra(SESSION_LABEL_KEY) ?: getString(R.string.general_default_session)
        Log.e(TAG,"Session label $sessionLabel")
        val durationLabel = "00:00"
        val completionPercent = 100
        val notification: Notification = composeNotification(durationLabel,completionPercent)
        startForeground(NOTIFICATION_ID,notification)
        Thread {
            val durationMillis = intent.getLongExtra(TIMER_DURATION_KEY,-1L)
            if (durationMillis == -1L) {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                return@Thread
            }
            val startTimeCalendar = Calendar.getInstance()
            startTimeCalendar.time = startTime
            startTimeCalendar.add(Calendar.MILLISECOND,durationMillis.toInt())
            val endTime = startTimeCalendar.time
            while (endTime >= Date()){
                val now = Date().time
                val total = endTime.time - startTime.time
                val remaining = endTime.time - now
                val elapsed = total - remaining
                val percent = (elapsed.toDouble() / total.toDouble()) * 100.0
                val durationFormated = formatDuration(remaining)
                val notification = composeNotification(durationFormated,percent.toInt())
                val notificationManager = getSystemService(NotificationManager::class.java)
                notificationManager.notify(NOTIFICATION_ID,notification)
                val updatesIntent = Intent(DURATION_UPDATE_BROADCAST_INTENT)
                updatesIntent.putExtra(DURATION_UPDATE_KEY,durationFormated)
                updatesIntent.putExtra(DURATION_FINISHED_KEY,false)
                updatePendingIntent.send(applicationContext,0,updatesIntent)
                Thread.sleep(1_000)
            }
            val updatesIntent = Intent(DURATION_UPDATE_BROADCAST_INTENT)
            updatesIntent.putExtra(DURATION_UPDATE_KEY,"00:00:00")
            updatesIntent.putExtra(DURATION_FINISHED_KEY,true)
            updatePendingIntent.send(applicationContext,0,updatesIntent)
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }.start()
        return START_NOT_STICKY
    }
    private fun composeNotification(durationLabel: String, completionPercent: Int): Notification {
        val contentIntent = Intent(applicationContext, TimerView::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            ACTIVITY_LAUNCH_INTENT,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,"Tick Tick Notifications",
                NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return NotificationCompat.Builder(applicationContext,NOTIFICATION_CHANNEL_ID)
            .setContentTitle(sessionLabel)
            .setContentText(durationLabel)
            .setProgress(100,completionPercent,false)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setContentIntent(contentPendingIntent)
            .build()

    }
    fun formatDuration(ms: Long): String {
        val totalSeconds = ms / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        //TODO: USER SET LOCALE SHOULD BE USED HERE
        return if (hours > 0) {
            String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds)
        }
    }
    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}