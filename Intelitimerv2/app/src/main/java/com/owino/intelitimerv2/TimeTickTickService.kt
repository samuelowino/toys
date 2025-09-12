package com.owino.intelitimerv2
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
import java.util.Date
import java.util.Locale
class TimeTickTickService: Service() {
    companion object {
        private const val TAG = "TimeTickTickService"
        const val TIMER_DURATION_MILLIS = "timer_duration_key"
        const val NOTIFICATION_CHANNEL_ID = "tick_tick_notifications"
        const val TIMER_EXTERNAL_UPDATE_KEY = "tick_tick_duration_formatted"
        const val TIMER_UPDATE_PENDING_INTENT = "tick_tick_pending_intent"
        const val TIMER_SESSION_LABEL_KEY = "tick_tick_session_label"
        const val NOTIFICATION_ID = 20211
        const val PENDING_INTENT_RESULT_CODE = 0
    }
    private lateinit var endTime: Date
    private lateinit var startTime: Date
    private lateinit var sessionLabel: String
    override fun onCreate() {
        super.onCreate()
        startTime = Date()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) return START_NOT_STICKY
        val pendingIntent: PendingIntent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TIMER_UPDATE_PENDING_INTENT, PendingIntent::class.java)
        } else {
            intent.getParcelableExtra(TIMER_UPDATE_PENDING_INTENT)
        }
        if (pendingIntent == null) {
            Log.e(TAG,"Service start command;pending intent null")
            return START_NOT_STICKY
        }
        val durationMillis = intent.getIntExtra(TIMER_DURATION_MILLIS, -1)
        if (durationMillis == -1){
            Log.e(TAG,"cannot start timer, invalid duration passed in")
            return START_NOT_STICKY
        }
        val label: String? = intent.getStringExtra(TIMER_SESSION_LABEL_KEY)
        if (label == null){
            Log.e(TAG,"Failed to launch timer, missing label")
            return START_NOT_STICKY
        }
        sessionLabel = label
        val launchNotification = composeNotification(formatDuration(0L),100)
        startForeground(NOTIFICATION_ID,launchNotification)
        Thread {
            val endTimeCalendar = Calendar.getInstance()
            endTimeCalendar.add(Calendar.MILLISECOND,durationMillis)
            endTime = endTimeCalendar.time
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
                val timerUpdateIntent = Intent()
                Log.e(TAG,"Send update :$durationFormated")
                timerUpdateIntent.putExtra(TIMER_EXTERNAL_UPDATE_KEY,durationFormated)
                pendingIntent.send(applicationContext,PENDING_INTENT_RESULT_CODE,timerUpdateIntent)
                Thread.sleep(1_000)
            }
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        }.start()
        return START_NOT_STICKY
    }
    private fun composeNotification(durationFormated: String, progress: Int): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Tick Tick Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val contentIntent = Intent(applicationContext, TimerView::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,0,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Builder(applicationContext,NOTIFICATION_CHANNEL_ID)
            .setContentTitle(sessionLabel)
            .setContentText(durationFormated)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(contentPendingIntent)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setProgress(100,progress,false)
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
    override fun onBind(intent: Intent?): IBinder? {
        return null //not accepting bindings
    }
}