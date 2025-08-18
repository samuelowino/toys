package com.owino.delegate_message.services
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.owino.delegate_message.networking.PixelServer
import com.owino.delegate_message.services.DownloadsIntentService.Companion.PHOTO_ID_KEY
import com.owino.delegate_message.services.DownloadsIntentService.Companion.PIXEL_API_KEY
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.Date
class DownloadScheduledJobService(): JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.e("DownloadScheduledJobService","start job")
        if (params == null) return false // work is not scheduled or ongoing
        Thread {
            val extras = params.extras
            val photoId = extras.getInt(PHOTO_ID_KEY,-1)
            val apiKey: String = extras.getString(PIXEL_API_KEY).orEmpty()
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