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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.owino.delegate_message.services.DownloadMessageHandler
import com.owino.delegate_message.services.DownloadsService
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val downloadButton = findViewById<Button>(R.id.download_button)
        val statusTextView = findViewById<TextView>(R.id.download_status_text)
        val downloadProgressView = findViewById<ProgressBar>(R.id.download_progress_view)
        downloadButton.setOnClickListener {
            downloadButton.visibility = View.GONE
            downloadProgressView.visibility = View.VISIBLE
            statusTextView.text = getString(R.string.title_downloading)
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
    }
    private fun readApiKey(): String {
        val inputStream = resources.openRawResource(R.raw.pixel_key)
        return inputStream.bufferedReader().use { it.readText() }
    }
}