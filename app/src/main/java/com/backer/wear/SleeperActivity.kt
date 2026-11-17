package com.backer.wear

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Ultra-lightweight launcher target for Sleeper.
 *
 * When launched via double-tap button mapping:
 * - If BackAccessibilityService is active: immediately triggers GLOBAL_ACTION_LOCK_SCREEN and terminates.
 * - If service is inactive: shows setup instructions.
 */
class SleeperActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        disableActivityTransitions()
        super.onCreate(savedInstanceState)
        handleSleepIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleSleepIntent()
    }

    private fun handleSleepIntent() {
        if (BackAccessibilityService.isConnected) {
            // Fast Path: Immediately finish and lock the screen / sleep
            finish()
            disableActivityTransitions()

            val success = BackAccessibilityService.performLockScreen()
            Log.d(TAG, "Lock screen executed: $success")
        } else {
            // Setup Path: Show setup instructions if service is disabled
            setTheme(R.style.Theme_Backer_Setup)
            setContentView(R.layout.activity_setup)
            setupViews()
        }
    }

    private fun setupViews() {
        val btnEnable = findViewById<Button>(R.id.btn_enable_service)
        btnEnable?.setOnClickListener {
            try {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to open accessibility settings", e)
            }
        }
        updateSetupUiIfVisible()
    }

    private fun updateSetupUiIfVisible() {
        val tvStatus = findViewById<TextView>(R.id.tv_status) ?: return
        val btnEnable = findViewById<Button>(R.id.btn_enable_service)

        val connected = BackAccessibilityService.isConnected || BackAccessibilityService.isServiceEnabledInSettings(this)
        if (connected) {
            tvStatus.text = getString(R.string.setup_status_active)
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.status_green))
            btnEnable?.visibility = View.GONE
        } else {
            tvStatus.text = getString(R.string.setup_status_inactive)
            tvStatus.setTextColor(ContextCompat.getColor(this, R.color.status_amber))
            btnEnable?.visibility = View.VISIBLE
        }
    }

    private fun disableActivityTransitions() {
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0)
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, 0, 0)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }
    }

    companion object {
        private const val TAG = "SleeperActivity"
    }
}
