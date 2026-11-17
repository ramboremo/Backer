# Backer & Sleeper — Wear OS Hardware Button Remapping

[![Platform](https://img.shields.io/badge/Platform-Wear%20OS-blue.svg)](https://developer.android.com/wear)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-purple.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-26%2B%20(Target%2034)-brightgreen.svg)](https://android-arsenal.com/api?level=26)
[![License](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

**Backer & Sleeper** is a lightweight, zero-latency physical button remapping utility designed for Wear OS smartwatches (tested and optimized for the **OnePlus Watch 2 / 2R**).

> **Note:** Installing the single **`backer-v1.0.0.apk`** installs **both** apps (**Backer** & **Sleeper**) onto your watch automatically!

---

## 💡 Why Backer & Sleeper?

Wear OS watches often lack a dedicated hardware **Back** button and a quick physical **Screen Off / Sleep** button. Most Wear OS watches (such as the OnePlus Watch 2 / 2R) feature a customizable secondary hardware key (the **bottom button**), but Wear OS settings only allow you to map buttons to *launch apps*, not system-level actions.

**Backer & Sleeper** solves this by providing two dedicated launcher targets with zero launch latency:
- 🔴 **Backer (Short Press / Single Click)** &rarr; Dispatches `GLOBAL_ACTION_BACK` (~35ms response, zero screen flicker).
- 🌙 **Sleeper (Double Press / Double Click)** &rarr; Dispatches `GLOBAL_ACTION_LOCK_SCREEN` (instantly turns off/locks the watch display).

Both apps share a single, ultra-lightweight Accessibility Service with **0% idle battery drain**.

---

## 🔘 Step-by-Step Setup & Button Mapping Guide

### Step 1: Install the APK
Download **`backer-v1.0.0.apk`** from the [GitHub Releases](https://github.com/ramboremo/Backer/releases/latest) page.

Install it via ADB over Wi-Fi or USB:
```powershell
adb install -r backer-v1.0.0.apk
```
*(Alternatively, sideload using apps like **Bugjaeger** on your phone or **Wear Installer 2** over Wi-Fi ADB).*

---

### Step 2: Enable the Accessibility Service
The app requires Accessibility permission to trigger the Back and Lock Screen actions globally.

**Option A (Automatic Setup Assistant):**
1. Open the watch app list / app drawer.
2. Tap on **Backer** or **Sleeper**.
3. Since the service is not yet enabled, the built-in Setup Assistant will appear.
4. Tap **"Open Settings"** to jump straight to the Accessibility menu.
5. Tap **Backer Service** and switch the toggle to **ON**.

**Option B (Manual):**
1. Open **Settings** on your watch.
2. Navigate to **Accessibility** &rarr; **Installed Services** (or **Downloaded Services**).
3. Tap **Backer Service** and toggle it **ON**.

---

### Step 3: Map the Physical Watch Buttons
Now configure your watch's customizable physical button (typically the **bottom button** on the OnePlus Watch 2 / 2R):

1. On your watch, open **Settings**.
2. Scroll to **Buttons & Gestures** (or **Advanced Features** &rarr; **Customizable Key / Secondary Button**).
3. **Configure Single Click / Short Press:**
   - Tap **Short Press** (or **Single Click**).
   - Scroll through your app list and select **Backer** (Red **✕** icon).
   - *Result: Pressing the bottom button anywhere in any app now takes you Back!*
4. **Configure Double Click / Double Press:**
   - Tap **Double Press** (or **Double Click**).
   - Scroll through your app list and select **Sleeper** (Midnight Purple Moon **🌙** icon).
   - *Result: Double-clicking the bottom button instantly puts the watch display to sleep and locks it!*

---

## ⚡ Technical Highlights

- **Instant Execution**: Transparent trampoline activity that executes within ~35ms without window animations.
- **Zero Battery Impact**: The Accessibility Service does not intercept keystrokes or run polling loops; it only activates when invoked by the button press.
- **No Root or ADB Commands Required After Install**: Once the APK is installed and the accessibility toggle is enabled, everything is configured directly through the standard watch settings.

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

# Output APK:
# app/build/outputs/apk/debug/app-debug.apk
```

---

## 📄 License

This project is licensed under the GNU General Public License v3.0 — see the [LICENSE](LICENSE) file for details.
