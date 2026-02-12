package com.lu4p.fokuslauncher.receiver

import android.content.Context
import android.content.Intent
import com.lu4p.fokuslauncher.data.repository.AppRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PackageChangeReceiverTest {

    private lateinit var context: Context
    private lateinit var appRepository: AppRepository
    private lateinit var receiver: PackageChangeReceiver

    @Before
    fun setup() {
        context = mockk(relaxed = true)
        appRepository = mockk(relaxed = true)
        receiver = PackageChangeReceiver()
        receiver.appRepository = appRepository
    }

    @Test
    fun `onReceive with PACKAGE_ADDED invalidates cache`() = runTest {
        val intent = Intent(Intent.ACTION_PACKAGE_ADDED).apply {
            data = android.net.Uri.parse("package:com.example.app")
        }

        receiver.onReceive(context, intent)
        advanceUntilIdle()

        coVerify { appRepository.invalidateCache() }
    }

    @Test
    fun `onReceive with PACKAGE_REMOVED invalidates cache`() = runTest {
        val intent = Intent(Intent.ACTION_PACKAGE_REMOVED).apply {
            data = android.net.Uri.parse("package:com.example.app")
        }

        receiver.onReceive(context, intent)
        advanceUntilIdle()

        coVerify { appRepository.invalidateCache() }
    }

    @Test
    fun `onReceive with PACKAGE_CHANGED invalidates cache`() = runTest {
        val intent = Intent(Intent.ACTION_PACKAGE_CHANGED).apply {
            data = android.net.Uri.parse("package:com.example.app")
        }

        receiver.onReceive(context, intent)
        advanceUntilIdle()

        coVerify { appRepository.invalidateCache() }
    }

    @Test
    fun `onReceive with unrelated action does not invalidate cache`() = runTest {
        val intent = Intent("android.intent.action.SOME_OTHER_ACTION")

        receiver.onReceive(context, intent)
        advanceUntilIdle()

        coVerify(exactly = 0) { appRepository.invalidateCache() }
    }
}
