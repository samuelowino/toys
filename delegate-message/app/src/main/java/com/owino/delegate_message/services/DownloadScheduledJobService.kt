package com.owino.delegate_message.services
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import androidx.core.app.NotificationCompat
import com.owino.delegate_message.R
import com.owino.delegate_message.networking.PixelServer
import com.owino.delegate_message.services.DownloadsIntentService.Companion.PHOTO_ID_KEY
import com.owino.delegate_message.services.DownloadsIntentService.Companion.PIXEL_API_KEY
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.Date
class DownloadScheduledJobService(): JobService() {
    companion object {
        const val notificationChannelId = "notification_channel_id"
    }
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.e("DownloadScheduledJobService","start job")
        if (params == null) return false // work is not scheduled or ongoing
        Thread {
            val extras = params.extras
            val photoId = extras.getInt(PHOTO_ID_KEY,-1)
            val apiKey: String = extras.getString(PIXEL_API_KEY).orEmpty()
            val channel = NotificationChannel(notificationChannelId,"Background Notifications",
                NotificationManager.IMPORTANCE_HIGH)
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            val notification = NotificationCompat.Builder(applicationContext,notificationChannelId)
                .setContentTitle("Downloading something")
                .setContentText("Downloading something in background please wait...")
                .setSmallIcon(R.drawable.download)
                .setProgress(1,1,true)
                .build()
            // you can use startForeground or setNotification both seem to work
            //startForeground(1,notification)

            val notificationId = 44552

            //This will show the notification until the user dismisses it
            //setNotification(params,notificationId,notification, JobService.JOB_END_NOTIFICATION_POLICY_DETACH)

            //The notification is removed automatically when the job ends
            setNotification(params,notificationId,notification, JobService.JOB_END_NOTIFICATION_POLICY_REMOVE)
            if (photoId == -1){
                Log.e("DownloadScheduledJobService", "Failed to download photo with id $photoId, invalid photo id")
            } else {
                val pixelServer = PixelServer()
                pixelServer.downloadPhoto(apiKey,photoId) { response,throwable ->
                    val result = StringBuilder()
                    if (response != null && response.isSuccessful && throwable == null){
                        result.append(response.message())
                    } else {
                        if (throwable != null) {
                            result.append(throwable.message)
                        }
                    }
                    if (applicationContext == null) {
                        Log.e("DownloadScheduledJobService","context is null")
                        return@downloadPhoto
                    }
                    //write results to common file
                    val appDir = File(applicationContext.filesDir,"download_result.txt")
                    if (!appDir.exists()){
                        appDir.mkdirs()
                    }
                    val outputFile = File(appDir,"download_result.txt")
                    if (!outputFile.exists()){
                        outputFile.createNewFile()
                    }
                    val bufferedWriter = BufferedWriter(FileWriter(outputFile))
                    bufferedWriter.use {
                        bufferedWriter.newLine()
                        bufferedWriter.write(Date().toString())
                        bufferedWriter.write(result.toString())
                        bufferedWriter.newLine()
                        result.clear()
                    }
                }
                stopForeground(Service.STOP_FOREGROUND_REMOVE)
                jobFinished(params,false) // pass true here if you want the job to run again
                Log.e("DownloadScheduledJobService","Finished scheduled job ✅")
            }
        }.start()
        return true //work is scheduled on a background thread
    }
    override fun onStopJob(params: JobParameters?): Boolean {
        Log.e("DownloadScheduledJobService","stop job")
        return true
    }
}