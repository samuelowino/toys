package com.owino.intelitimer
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.icu.util.Calendar
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import androidx.core.app.NotificationCompat
import java.util.Date
import java.util.Locale
class TimerTickTickService(): Service() {
    companion object {
        const val TASK_LABEL_KEY = "task_label_key"
        const val REGISTER_CLIENT = 1
        const val TIMER_UPDATE = 2
        const val TIME_DIFF_MILLIS = "time_diff_millis"
        const val TIME_DIFF_PERCENTAGE = "time_diff_millis_percent"
    }
    private lateinit var sessionLabel: String
    private lateinit var startTime: Date
    private lateinit var endTime: Date
    private var activityMessenger: Messenger? = null
    private val notificationId: Int = 31231
    private val channelId = "timer_notification_channel"
    override fun onCreate() {
        super.onCreate()
        //support set up
        sessionLabel = "Time Label"
        startTime = Date()
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE,1)
        endTime = calendar.time
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) return START_NOT_STICKY
        if (intent.action == TimerBroadcastReceiver.STOP_TIMER_ACTION){
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            return START_NOT_STICKY
        } else {
            val label: String? = intent.getStringExtra(TASK_LABEL_KEY)
            if (label != null) sessionLabel = label
            Thread {
                val notification = prepareNotification()
                startForeground(notificationId,notification)
                while (endTime >= Date()){
                    val now = Date().time
                    val total = endTime.time - startTime.time
                    val remaining = endTime.time - now
                    val elapsed = total - remaining
                    val percent = (elapsed.toDouble() / total.toDouble()) * 100.0
                    updateNotification(remaining, percent)
                    if (activityMessenger != null)
                     sendMessageToListeners(remaining,percent.toInt())
                    Thread.sleep(1_000)
                }
                stopForeground(STOP_FOREGROUND_DETACH)
            }.start()
            return START_NOT_STICKY
        }
    }

    private fun prepareNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId, "Timer Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return composeNotification(0L,100.0)
    }

    private fun composeNotification(timeDiffMillis: Long, timeDiffPercent: Double): Notification {
        val requestCode = 4511
        val notificationBroadcastIntent =
            Intent(applicationContext, TimerBroadcastReceiver::class.java)
        notificationBroadcastIntent.action = TimerBroadcastReceiver.STOP_TIMER_ACTION
        val stopTimerPendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            requestCode,
            notificationBroadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val durationLabel = formatDuration(timeDiffMillis)
        val actionLabel = if(timeDiffPercent >= 100.0) "Done" else "Stop Timer"
        return NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.timer_icon)
            .setContentTitle(sessionLabel)
            .setProgress(100, timeDiffPercent.toInt(), false)
            .setContentText(durationLabel)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.ic_launcher_background, actionLabel, stopTimerPendingIntent)
            .build()
    }

    private fun updateNotification(timeDiffMillis: Long, timeDiffPercentage: Double) {
        val notification = composeNotification(timeDiffMillis,timeDiffPercentage)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(notificationId,notification)
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

    fun stopTimer(){
        stopSelf()
    }
    fun pauseTimer(){
        //todo build mechanism to pause
    }
    override fun onBind(intent: Intent?): IBinder? {
        //return Messenger(TimeMessageHandler()).binder
        return null
    }
    private fun sendMessageToListeners(timeDiffMillis: Long, timeDiffPercentage: Int){
        val msg = Message.obtain(null,TIMER_UPDATE)
        val bundle = Bundle()
        bundle.putLong(TIME_DIFF_MILLIS,timeDiffMillis)
        bundle.putInt(TIME_DIFF_PERCENTAGE,timeDiffPercentage)
        msg.data = bundle
        activityMessenger?.let {
            activityMessenger!!.send(msg)
        }
    }
    inner class TimeMessageHandler: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what == REGISTER_CLIENT) {
                activityMessenger = msg.replyTo
            }
        }
        fun getService(): TimerTickTickService = this@TimerTickTickService
    }
}