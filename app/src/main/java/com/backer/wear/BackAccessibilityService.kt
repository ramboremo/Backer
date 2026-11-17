package com.backer.wear

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import java.lang.ref.WeakReference

/**
 * Background AccessibilityService that performs GLOBAL_ACTION_BACK and GLOBAL_ACTION_LOCK_SCREEN on demand.
 */
class BackAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        serviceReference = WeakReference(this)
        Log.d(TAG, "BackAccessibilityService connected.")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // No event listening required; we only trigger global actions.
    }

    override fun onInterrupt() {
        Log.d(TAG, "BackAccessibilityService interrupted.")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceReference = null
        Log.d(TAG, "BackAccessibilityService destroyed.")
    }

    companion object {
        private const val TAG = "BackerService"
        private var serviceReference: WeakReference<BackAccessibilityService>? = null

        /**
         * Returns true if the service is currently connected and ready to perform actions.
         */
        val isConnected: Boolean
            get() = serviceReference?.get() != null

        /**
         * Executes the Android system-wide Back action.
         * @return true if the global action was successfully dispatched to the system.
         */
        fun performBack(): Boolean {
            val service = serviceReference?.get()
            return if (service != null) {
                val success = service.performGlobalAction(GLOBAL_ACTION_BACK)
                Log.d(TAG, "performGlobalAction(GLOBAL_ACTION_BACK) result: $success")
                success
            } else {
                Log.w(TAG, "Cannot perform Back: BackAccessibilityService is not connected.")
                false
            }
        }

        /**
         * Puts the watch to sleep / locks the screen.
         * @return true if the global lock action was successfully dispatched.
         */
        fun performLockScreen(): Boolean {
            val service = serviceReference?.get()
            return if (service != null) {
                val success = service.performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
                Log.d(TAG, "performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN) result: $success")
                success
            } else {
                Log.w(TAG, "Cannot perform LockScreen: BackAccessibilityService is not connected.")
                false
            }
        }

        /**
         * Checks whether this service is enabled in Android Secure Settings.
         */
        fun isServiceEnabledInSettings(context: Context): Boolean {
            val expectedServiceName = "${context.packageName}/${BackAccessibilityService::class.java.canonicalName}"
            val enabledServicesSetting = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            ) ?: return false

            val colonSplitter = TextUtils.SimpleStringSplitter(':')
            colonSplitter.setString(enabledServicesSetting)

            while (colonSplitter.hasNext()) {
                val componentName = colonSplitter.next()
                if (componentName.equals(expectedServiceName, ignoreCase = true) ||
                    componentName.endsWith("/${BackAccessibilityService::class.java.name}")
                ) {
                    return true
                }
            }
            return false
        }
    }
}
