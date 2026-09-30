package com.iaseasyway.academy.data.security

import android.app.Activity
import android.os.Build
import android.os.Debug
import android.view.WindowManager
import java.io.File

object SecurityManager {

    /**
     * Enforces hardware-backed WindowManager FLAG_SECURE.
     * Prevents Android system and third-party tools from taking screenshots,
     * screencasting, or caching recent task thumbnails of proprietary question papers.
     */
    fun applyScreenshotProtection(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    /**
     * Verifies device integrity against rooted environments and active debuggers
     * to protect against reverse engineering and memory scraping.
     */
    fun isDeviceTampered(): Boolean {
        return isRootedDevice() || isDebuggerAttached()
    }

    fun isDebuggerAttached(): Boolean {
        return Debug.isDebuggerConnected() || Debug.waitingForDebugger()
    }

    private fun isRootedDevice(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (path in paths) {
            try {
                if (File(path).exists()) return true
            } catch (_: Exception) {
                // Ignore security manager sandbox exceptions
            }
        }
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    /**
     * Obfuscation and dynamic token check for API requests.
     */
    fun generateApiHeaders(token: String? = null): Map<String, String> {
        val headers = mutableMapOf(
            "X-Client-Platform" to "Android-IEW-Native",
            "X-Client-Version" to "1.0.0",
            "X-DRM-Protection" to "FLAG_SECURE_R8"
        )
        if (!token.isNullOrBlank()) {
            headers["Authorization"] = "Bearer $token"
        }
        return headers
    }
}
