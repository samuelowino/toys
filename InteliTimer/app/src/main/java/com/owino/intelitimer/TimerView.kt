package com.owino.intelitimer
import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class TimerView: AppCompatActivity(){
    private var timerService: TimerTickTickService? = null
    private val notificationPermissionRequestCode: Int = 76622
    private var bound = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer_view_layout)
        val timerView: TextView = findViewById(R.id.time_text_view)
        timerView.text = "00:00:00"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionGranted = ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS)
            if (permissionGranted != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    notificationPermissionRequestCode
                )
            } else {
                startService()
            }
        } else {
            startService()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == notificationPermissionRequestCode){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if(!grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        notificationPermissionRequestCode
                    )
                }
            }
        }
    }
    private fun startService(){
        val intent = Intent(applicationContext, TimerTickTickService::class.java)
        intent.putExtra(TimerTickTickService.TASK_LABEL_KEY,"Some Task")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
        //Binding to the service still fails
       // bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)
    }
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val localBinder = binder as TimerTickTickService.TimeMessageHandler
            timerService = localBinder.getService()
            bound = true
            Log.e("MainActivity", "Service connected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
            timerService = null
            Log.e("MainActivity", "Service disconnected")
        }
    }
}