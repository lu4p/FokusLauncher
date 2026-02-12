package com.lu4p.fokuslauncher.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lu4p.fokuslauncher.data.repository.AppRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Broadcast receiver that listens for package installation and removal events
 * to automatically refresh the app list in the launcher.
 */
@AndroidEntryPoint
class PackageChangeReceiver : BroadcastReceiver() {

    @Inject
    lateinit var appRepository: AppRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_PACKAGE_ADDED,
            Intent.ACTION_PACKAGE_REMOVED,
            Intent.ACTION_PACKAGE_CHANGED -> {
                // Invalidate the app repository cache so the next load will get fresh data
                scope.launch {
                    appRepository.invalidateCache()
                }
            }
        }
    }
}
