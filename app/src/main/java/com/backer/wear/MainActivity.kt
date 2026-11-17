package com.backer.wear

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * Ultra-lightweight launcher target.
 *
 * When launched via physical button:
 * - If BackAccessibilityService is active: immediately closes itself and executes GLOBAL_ACTION_BACK.
 * - If service is inactive: shows setup instructions with a direct link to Accessibility Settings.
 */
class MainActivity : Activity() {

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        // Disable window enter/exit animations completely to ensure zero flicker
        disableActivityTransitions()
        super.onCreate(savedInstanceState)

        handleLaunchIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleLaunchIntent()
    }

    override fun onResume() {
        super.onResume()
        // If the setup screen is showing, refresh its status when returning from Settings
        updateSetupUiIfVisible()
    }

    private fun handleLaunchIntent() {
        if (BackAccessibilityService.isConnected) {
            // Fast Path: Service is ready.
            // 1. Finish this activity immediately so window focus is returned to the underlying app
            finish()
            disableActivityTransitions()

            // 2. Dispatch GLOBAL_ACTION_BACK to the restored underlying window
            mainHandler.postDelayed({
                val success = BackAccessibilityService.performBack()
                Log.d(TAG, "Back action executed: $success")
            }, FOCUS_HANDOVER_DELAY_MS)
        } else {
            // Setup Path: Accessibility Service is not yet enabled.
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
        private const val TAG = "BackerActivity"
        // 35ms gives the Android WindowManager 1-2 frames to restore focus to the previous task
        private const val FOCUS_HANDOVER_DELAY_MS = 35L
    }
}
