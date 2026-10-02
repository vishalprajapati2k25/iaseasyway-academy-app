# 🚀 IEW Academy - Production Release Information

Official release documentation for **IEW Academy** ([iaseasyway.com](https://www.iaseasyway.com)).

---

## 📥 Official Release Downloads (Hosted via GitHub Releases CDN)

In accordance with modern software release standards and Google Play Compliance principles, compiled binaries (`.apk`, `.aab`) are hosted on **GitHub Releases** rather than tracked inside the Git repository tree, preventing repository bloat while guaranteeing high-speed downloads:

| File | Type | Size | Official Direct Download Link | Description |
| :--- | :--- | :---: | :--- | :--- |
| **`IEWAcademy-v1.0.0-release.apk`** | Signed Release APK | 5.5 MB | [⬇️ **Download Release APK**](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/download/v1.0.0/IEWAcademy-v1.0.0-release.apk) | Standalone signed & R8-minified APK for production testing & sideloading. |
| **`IEWAcademy-v1.0.0-debug.apk`** | Debug Signed APK | 15.0 MB | [⬇️ **Download Debug APK**](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/download/v1.0.0/IEWAcademy-v1.0.0-debug.apk) | Debug-signed APK with Logcat diagnostics enabled. |
| **`IEWAcademy-v1.0.0-release.aab`** | Production AAB | 16.3 MB | [⬇️ **Download Production AAB**](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/download/v1.0.0/IEWAcademy-v1.0.0-release.aab) | Google Play Store publication bundle (Android 15 / API 35 compliant). |

> 🏷️ **GitHub Release Tag**: [`v1.0.0`](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/tag/v1.0.0)

---

## 🔐 Cryptographic Integrity & Checksums

| File | SHA-256 Checksum |
| :--- | :--- |
| `IEWAcademy-v1.0.0-release.apk` | `eecb86fb6ac98cdaccbf75c497fe34f5948a9844773697f4e05abc5b6208cafc` |
| `IEWAcademy-v1.0.0-debug.apk` | `5624f4fcac17345a7ed3ee339f58026e77d1223814aaf8d4e9928e09cddb7351` |
| `IEWAcademy-v1.0.0-release.aab` | `bc2c66b436df250490584c88eabc7e64c20561fe59459e0f8a9ccda67ce6e792` |

---

## 📋 Release Metadata & Compliance

* **Version Name**: `1.0.0`
* **Version Code**: `1`
* **Target SDK**: `API 35` (Android 15)
* **Min SDK**: `API 24` (Android 7.0)
* **Privacy Policy URL**: [https://academy.itfreesource.com/apps/iew-academy/privacy.html](https://academy.itfreesource.com/apps/iew-academy/privacy.html)
* **Store Listing Assets**: Located at `store-assets/` (512x512 Icon & 1024x500 Feature Graphic)

---

## 📲 Installation Instructions

### Via ADB
```bash
adb install -r IEWAcademy-v1.0.0-release.apk
```

### Direct Sideload on Android
1. Download [**`IEWAcademy-v1.0.0-release.apk`**](https://github.com/vishalprajapati2k25/iaseasyway-academy-app/releases/download/v1.0.0/IEWAcademy-v1.0.0-release.apk) on your Android device.
2. Tap the downloaded file in your Notification Center or Downloads folder.
3. Allow "Install unknown apps" for your browser if prompted.
4. Tap **Install** and open **IEW Academy**.
