# Backer & Sleeper — Wear OS Hardware Button Remapping

[![Platform](https://img.shields.io/badge/Platform-Wear%20OS-blue.svg)](https://developer.android.com/wear)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-purple.svg)](https://kotlinlang.org)
[![API](https://img.shields.io/badge/API-26%2B%20(Target%2034)-brightgreen.svg)](https://android-arsenal.com/api?level=26)
[![License](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)

**Backer & Sleeper** is a simple utility for Wear OS smartwatches (like the OnePlus Watch 2 / 2R) that lets you use your watch's physical button as:
- A dedicated **Back button**
- A **Sleep / Screen Off button**

> **Note:** Installing the single **`backer-v1.0.0.apk`** installs both apps (**Backer** and **Sleeper**) onto your watch together.

---

## What It Does

On Wear OS, watches often have a customizable hardware button (usually the bottom button), but the system settings only let you choose an app to open when you press it—not a system action like "go back" or "turn off screen".

Backer & Sleeper provides two small launcher apps to solve this:
- **Backer** (Red ✕ icon): Triggers the Android Back action and immediately closes.
- **Sleeper** (Purple Moon 🌙 icon): Turns off and locks the watch screen and immediately closes.

Both apps share a single accessibility service that carries out the back and lock screen actions.

---

## Setup & Button Mapping Guide

### Step 1: Install the APK
Download **`backer-v1.0.0.apk`** from the [GitHub Releases](https://github.com/ramboremo/Backer/releases/latest) page.

Install it to your watch using ADB:
```powershell
adb install -r backer-v1.0.0.apk
```
*(You can also use apps like **Bugjaeger** on your phone or **Wear Installer 2** over Wi-Fi ADB).*

---

### Step 2: Enable the Accessibility Service
The app needs accessibility access to trigger the back and lock screen actions.

**Easy way:**
1. Open the app drawer on your watch and tap **Backer** or **Sleeper**.
2. A setup screen will open. Tap **"Open Settings"**.
3. Tap **Backer Service** and turn it **ON**.

**Manual way:**
1. Go to **Settings** on your watch.
2. Open **Accessibility** &rarr; **Installed Services** (or **Downloaded Services**).
3. Find **Backer Service** and turn it **ON**.

---

### Step 3: Map the Watch Button
Map your watch's customizable physical button (typically the bottom button on OnePlus Watch 2 / 2R):

1. Open **Settings** on your watch.
2. Go to **Buttons & Gestures** (or **Customizable Key / Secondary Button**).
3. **Single Click / Short Press:** Tap it, select **Backer** (Red ✕ icon).
   - Pressing the button will now take you back, just like a hardware back button.
4. **Double Click / Double Press:** Tap it, select **Sleeper** (Purple Moon 🌙 icon).
   - Double-pressing the button will now turn off and lock your screen.

---

## Building From Source

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

## License

This project is licensed under the GNU General Public License v3.0 — see the [LICENSE](LICENSE) file for details.
