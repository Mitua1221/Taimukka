package com.arjental.taimukka.other.platform

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import dagger.android.AndroidInjection

class PermissionsCheckerService : Service() {

    private val binder = PermissionsCheckerServiceBinder()

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class PermissionsCheckerServiceBinder : Binder() {
        val service = this@PermissionsCheckerService
    }

    class PermissionsCheckerServiceConnection : ServiceConnection {
        var binder: PermissionsCheckerService.PermissionsCheckerServiceBinder? = null
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            this.binder = binder as PermissionsCheckerService.PermissionsCheckerServiceBinder
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            this.binder = null
        }
    }

    companion object {
        fun register(context: Context, connection: PermissionsCheckerServiceConnection) {
            val flags = Context.BIND_ADJUST_WITH_ACTIVITY
            val intent = Intent(context, PermissionsCheckerService::class.java)
            context.bindService(intent, connection, flags)
        }
    }


}
