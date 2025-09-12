package com.owino.intelitimer
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
class TimerBroadcastReceiver: BroadcastReceiver() {
    companion object {
        const val INTENT_ACTION = "intent_action_type"
        const val STOP_TIMER_ACTION = "stop_action_intent"
        const val PAUSE_TIMER_ACTION = "pause_action_intent"
        const val RESUME_TIMER_ACTION = "resume_action_intent"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return
        if (intent.action == STOP_TIMER_ACTION){
            val stopTimerIntent = Intent(
                context,
                TimerTickTickService::class.java)
            stopTimerIntent.action = STOP_TIMER_ACTION
            context.stopService(stopTimerIntent)
        }
    }
}