# Backer & Sleeper — Wear OS Hardware Button Remapping

[![Platform](https://img.shields.io/badge/Platform-Wear%20OS-blue.svg)](https://developer.android.com/wear)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-purple.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-26%2B%20(Target%2034)-brightgreen.svg)](https://android-arsenal.com/api?level=26)
[![License](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

**Backer & Sleeper** is a lightweight, zero-latency hardware key remapping utility designed for Wear OS smartwatches (tested extensively on the **OnePlus Watch 2 / 2R**).

Wear OS devices often lack a dedicated hardware "Back" button or a fast, reliable single-action screen lock button. Backer & Sleeper exposes two ultra-lightweight launcher targets that map directly to Android global actions via an optimized Accessibility Service:

- 🔴 **Backer (Single Press)** &rarr; **Hardware Back** (`GLOBAL_ACTION_BACK`)
- 🌙 **Sleeper (Double Press)** &rarr; **Lock / Sleep Screen** (`GLOBAL_ACTION_LOCK_SCREEN`)

---

## ⚡ Key Highlights

- **Instant Response (~35ms)**: Uses a fully transparent, non-animating trampoline activity that delegates immediately to `AccessibilityService.performGlobalAction()`.
- **Zero Battery Drain**: Event-driven architecture with zero background polling, zero listeners, and minimal memory footprint.
- **No Root Required**: Operates within standard Android / Wear OS accessibility APIs.
- **Clean Separation**: Backer and Sleeper show up as independent apps in Wear OS button configuration menus, while sharing a single underlying accessibility service.

---

## 🔘 Setup & Configuration

### 1. Install the APK
Download `backer-v1.0.0.apk` from the [Releases](https://github.com/ramboremo/Backer/releases) page and install it using ADB or your preferred tool:

```powershell
adb install -r backer-v1.0.0.apk
```

*(Alternatively, use **Bugjaeger** on your phone or **Wear Installer 2** over Wi-Fi ADB).*

### 2. Enable Accessibility Service
1. On your watch, navigate to **Settings** &rarr; **Accessibility**.
2. Scroll to **Installed Services** (or **Downloaded Services**).
3. Find **Backer Service** and toggle it **ON**.

### 3. Assign Watch Buttons
On OnePlus Watch 2 / 2R (or similar Wear OS watches):
1. Open **Settings** &rarr; **Buttons & Gestures** (or **Customizable Key / Secondary Button**).
2. Set **Short Press / Single Click** &rarr; **Backer** (Red ✕ icon).
3. Set **Double Press / Double Click** &rarr; **Sleeper** (Purple Moon 🌙 icon).

---

## 🛠️ Building From Source

### Prerequisites
- JDK 17 or JDK 21
- Android SDK (API 34, Build Tools 34.0.0+)

### Build Steps
```powershell
# Clone the repository
git clone https://github.com/ramboremo/Backer.git
cd Backer

# Build debug APK
.\gradlew.bat assembleDebug

# Output APK is located at:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 📄 License

This project is licensed under the GNU General Public License v3.0 — see the [LICENSE](LICENSE) file for details.
