package com.owino.delegate_message.services
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlin.random.Random
class WordlyService: Service() {
    private val binder: IBinder = LocalBinder()
    inner class LocalBinder : Binder() {
        fun getService(): WordlyService = this@WordlyService
    }
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
    fun getRandomWords(): List<String> {
        val consonants = listOf("b", "c", "d", "f", "g", "h", "j", "k", "l", "m",
            "n", "p", "r", "s", "t", "v", "w", "z", "ch", "sh", "th")
        val vowels = listOf(
            "a", "e", "i", "o", "u", "ai", "ea", "ou", "ie"
        )
        val wordBuilder = StringBuilder()
        val words = mutableListOf<String>()
        for (i in 0..15){
            val consonant = consonants[Random.nextInt(consonants.size)]
            val vowel = vowels[Random.nextInt(vowels.size)]
            val word = wordBuilder.append(consonant).append(vowel)
            val capitalized = word.substring(0,1).uppercase() + word.substring(1)
            words.add(capitalized)
            Log.w("WordlyService","Word $word")
            wordBuilder.clear()
        }
        return words
    }
}