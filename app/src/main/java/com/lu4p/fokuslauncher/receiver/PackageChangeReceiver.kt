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

    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.data?.schemeSpecificPart
        if (packageName.isNullOrBlank()) return
        
        when (intent.action) {
            Intent.ACTION_PACKAGE_ADDED -> {
                executeAsync { appRepository.onPackageAdded(packageName) }
            }
            Intent.ACTION_PACKAGE_REMOVED -> {
                executeAsync { appRepository.onPackageRemoved(packageName) }
            }
            Intent.ACTION_PACKAGE_CHANGED -> {
                executeAsync { appRepository.onPackageChanged(packageName) }
            }
        }
    }

    private fun executeAsync(block: suspend () -> Unit) {
        val pendingResult = goAsync()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        
        scope.launch {
            try {
                block()
            } finally {
                pendingResult.finish()
            }
        }
    }
}
