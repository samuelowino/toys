package com.owino.delegate_message.services
import android.os.Handler
import android.os.Message
import android.util.Log
class DownloadMessageHandler(val action: (message: Message) -> Unit): Handler() {
    override fun handleMessage(message: Message) {
        Log.w("DownloadMessageHandler","Handling message $message")
        action.invoke(message)
    }
}