package com.owino.delegate_message.services
import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.owino.delegate_message.networking.PixelServer
class DownloadsService(serviceName: String = "DownloadsService"): IntentService(serviceName) {
    companion object {
        const val PHOTO_ID_KEY = "PHOTO_ID_KEY"
        const val PIXEL_API_KEY = "PIXEL_API_KEY"
        const val MESSENGER_KEY = "CALLBACK_MESSAGE_KEY"
        const val DOWNLOAD_SUCCESS = 0
        const val DOWNLOAD_FAILED = -1
        const val DOWNLOAD_RESPONSE_KEY = "DOWNLOAD_RESPONSE"
    }
    @Deprecated("Intentionally messing with a deprecated API")
    override fun onHandleIntent(intent: Intent?) {
        Log.w("DownloadsService","handle download intent service")
        intent?.let {
            val pixelServer = PixelServer()
            val photoId = it.getIntExtra(PHOTO_ID_KEY,-1)
            val apiKey: String = it.getStringExtra(PIXEL_API_KEY).orEmpty()
            val messenger: Messenger? = it.getParcelableExtra(MESSENGER_KEY,Messenger::class.java)
            val message: Message = Message.obtain()
            if (photoId == -1){
                Log.e("DownloadService", "Failed to download photo with id $photoId, invalid photo id")
                message.what = DOWNLOAD_FAILED
                messenger?.send(message)
            } else {
                pixelServer.downloadPhoto(apiKey,photoId) { response,throwable ->
                    val bundle = Bundle()
                    if (response != null && response.isSuccessful && throwable == null){
                        bundle.putString(DOWNLOAD_RESPONSE_KEY,response.message())
                    } else {
                        if (throwable != null) {
                            bundle.putString(DOWNLOAD_RESPONSE_KEY,throwable.message)
                        }
                    }
                    message.what = DOWNLOAD_SUCCESS
                    message.data = bundle
                    messenger?.send(message)
                }
            }
        }
    }
}