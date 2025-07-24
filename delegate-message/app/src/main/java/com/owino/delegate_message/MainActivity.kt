package com.owino.delegate_message
import android.content.Intent
import android.os.Bundle
import android.os.Messenger
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.owino.delegate_message.networking.PixelServer
import com.owino.delegate_message.services.DownloadMessageHandler
import com.owino.delegate_message.services.DownloadsService
import kotlin.random.Random
class MainActivity : AppCompatActivity() {
    private lateinit var statusTextView: TextView
    private lateinit var downloadButton: Button
    private lateinit var downloadProgressView: ProgressBar
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
        downloadButton.setOnClickListener {
            if (downloadOptionSwitch.isChecked) {
                downloadInProcess()
            } else {
                downloadWitIntentService()
            }
        }
        randomButton.setOnClickListener {
            randomNumberTextView.text = "Random ${Random.nextInt()}"
        }
    }
    private fun downloadWitIntentService() {
        downloadButton.visibility = View.GONE
        downloadProgressView.visibility = View.VISIBLE
        statusTextView.text = getString(R.string.title_downloading_with_intent_service)
        val message = Messenger(DownloadMessageHandler {
            val successStatus = it.what
            if (successStatus == DownloadsService.DOWNLOAD_FAILED) {
                statusTextView.text = getString(R.string.title_download_failed)
            } else {
                statusTextView.text = getString(R.string.title_finished_download)
            }
            downloadButton.visibility = View.VISIBLE
            downloadProgressView.visibility = View.GONE
        })
        val downloadIntent = Intent(applicationContext,DownloadsService::class.java)
        val photoId = 2014422
        val apiKey = readApiKey()
        downloadIntent.putExtra(DownloadsService.PIXEL_API_KEY,apiKey)
        downloadIntent.putExtra(DownloadsService.PHOTO_ID_KEY,photoId)
        downloadIntent.putExtra(DownloadsService.MESSENGER_KEY, message)
        startService(downloadIntent)
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
}