package com.owino.whackamole
import android.content.Context
import androidx.multidex.MultiDexApplication
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
class ApplicationContext: MultiDexApplication() {
    private var executorService: ExecutorService? = null
    override fun onCreate() {
        super.onCreate()
    }
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
    fun executorService(): ExecutorService {
        if (executorService == null) {
            val corePoolSize = 2
            val maxPoolSize = 3
            val keepAliveTime: Long = 5
            executorService = ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                ArrayBlockingQueue(2)
            )
        }
        return executorService!!
    }
}