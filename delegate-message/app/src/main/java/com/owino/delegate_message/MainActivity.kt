package com.owino.delegate_message
import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.os.Messenger
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.owino.delegate_message.networking.PixelServer
import com.owino.delegate_message.services.DownloadMessageHandler
import com.owino.delegate_message.services.DownloadScheduledJobService
import com.owino.delegate_message.services.DownloadsIntentService
import com.owino.delegate_message.services.WordlyService
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.random.Random
class MainActivity : AppCompatActivity() {
    private lateinit var statusTextView: TextView
    private lateinit var downloadButton: Button
    private lateinit var downloadProgressView: ProgressBar
    private var isWordlyServiceConnected = false
    private lateinit var wordlyService: WordlyService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        downloadButton = findViewById(R.id.download_button)
        statusTextView = findViewById(R.id.download_status_text)
        downloadProgressView = findViewById(R.id.download_progress_view)
        val randomNumberTextView = findViewById<TextView>(R.id.random_number_title_view)
        val randomButton = findViewById<Button>(R.id.general_random_button)
        val downloadOptionSwitch = findViewById<SwitchCompat>(R.id.download_style_switch)
        val jobSchedulerSwitch = findViewById<SwitchCompat>(R.id.job_scheduler_style_switch)
        val checkJobResult = findViewById<Button>(R.id.general_job_result_button)
        val outputTextView = findViewById<TextView>(R.id.scheduled_job_result_view)
        downloadButton.setOnClickListener {
            if (downloadOptionSwitch.isChecked) {
                downloadInProcess()
            } else if (jobSchedulerSwitch.isChecked){
                downloadWithJobSchedulerService()
            } else {
                downloadWitIntentService()
            }
        }
        checkJobResult.setOnClickListener {
            val appDir = File(applicationContext.filesDir,"download_result.txt")
            if (!appDir.exists()) {
                appDir.mkdirs()
            }
            val outputFile = File(appDir,"download_result.txt")
            if (!outputFile.exists()) return@setOnClickListener
            val bufferedReader = BufferedReader(FileReader(outputFile))
            var resultText: String = ""
            bufferedReader.use {
                val strBuilder = StringBuilder()
                var line: String? = ""
                while (line != null){
                    line = bufferedReader.readLine()
                    if (line != null) {
                        strBuilder.append(line)
                    }
                }
                resultText = strBuilder.toString()
                strBuilder.clear()
            }
            outputTextView.text = resultText
        }
        randomButton.setOnClickListener {
            randomNumberTextView.text = "Random ${Random.nextInt()}"
        }
    }
    override fun onStart() {
        super.onStart()
        val permissionGrantedStatus = ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        if (permissionGrantedStatus == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.POST_NOTIFICATIONS),4555)
        }
        val intent = Intent(applicationContext, WordlyService::class.java)
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)
    }
    override fun onStop() {
        super.onStop()
        if (isWordlyServiceConnected) {
            unbindService(serviceConnection)
            isWordlyServiceConnected = false
        }
    }
    private fun downloadWitIntentService() {
        downloadButton.visibility = View.GONE
        downloadProgressView.visibility = View.VISIBLE
        statusTextView.text = getString(R.string.title_downloading_with_intent_service)
        val message = Messenger(DownloadMessageHandler {
            val successStatus = it.what
            if (successStatus == DownloadsIntentService.DOWNLOAD_FAILED) {
                statusTextView.text = getString(R.string.title_download_failed)
            } else {
                statusTextView.text = getString(R.string.title_finished_download)
            }
            downloadButton.visibility = View.VISIBLE
            downloadProgressView.visibility = View.GONE
        })
        val downloadIntent = Intent(applicationContext,DownloadsIntentService::class.java)
        val photoId = 2014422
        val apiKey = readApiKey()
        downloadIntent.putExtra(DownloadsIntentService.PIXEL_API_KEY,apiKey)
        downloadIntent.putExtra(DownloadsIntentService.PHOTO_ID_KEY,photoId)
        downloadIntent.putExtra(DownloadsIntentService.MESSENGER_KEY, message)
        startService(downloadIntent)
    }
    private fun downloadWithJobSchedulerService(){
        val photoId = 2014422
        val apiKey = readApiKey()
        val serviceComponent: ComponentName = ComponentName(
            applicationContext,
            DownloadScheduledJobService::class.java
        )
        val extras: PersistableBundle = PersistableBundle().apply {
            putInt(DownloadsIntentService.PHOTO_ID_KEY,photoId)
            putString(DownloadsIntentService.PIXEL_API_KEY,apiKey)
        }
        val jobId = 993311
        val jobInfo: JobInfo = JobInfo.Builder(jobId,serviceComponent)
            .setExtras(extras)
            .build()
        val scheduler: JobScheduler = applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.schedule(jobInfo)
    }
    private fun readApiKey(): String {
        val inputStream = resources.openRawResource(R.raw.pixel_key)
        return inputStream.bufferedReader().use { it.readText() }
    }
    private fun downloadInProcess() {
        downloadButton.visibility = View.GONE
        downloadProgressView.visibility = View.VISIBLE
        statusTextView.text = getString(R.string.title_downloading_without_intent_service)
        val pixelServer = PixelServer()
        pixelServer.downloadPhoto(readApiKey(),Random.nextInt()) { response,throwable ->
            if (response != null && response.isSuccessful && throwable == null){
                statusTextView.text = getString(R.string.title_finished_download)
            } else {
                if (throwable != null) {
                    statusTextView.text = getString(R.string.title_download_failed)
                }
            }
            downloadButton.visibility = View.VISIBLE
            downloadProgressView.visibility = View.GONE
        }
    }
    val serviceConnection: ServiceConnection = object: ServiceConnection {
        override fun onServiceConnected(component: ComponentName?, iBinder: IBinder?) {
            val binder: WordlyService.LocalBinder = iBinder as WordlyService.LocalBinder
            wordlyService = binder.getService()
            isWordlyServiceConnected = true
            streamWords()
            Log.w("ServiceConnection", "Service connection with ${component?.className} established")
        }
        override fun onServiceDisconnected(component: ComponentName?) {
            isWordlyServiceConnected = false
            Log.e("ServiceConnection","Failed to establish connection to ${component?.className}")
        }
    }
    private fun streamWords() {
        runOnUiThread {
            val words = wordlyService.getRandomWords()
            for (string in words) {
                Toast.makeText(applicationContext,"Word: $string", Toast.LENGTH_SHORT).show()
            }
        }
    }
}