# Proguard & R8 Rules for IEW Academy (IAS EasyWay) Google Play Release
# Target: Maximum protection against unauthorized decompilation, reverse engineering, and question asset scraping.

# 1. Jetpack Compose & UI Runtimes
-keep class androidx.compose.** { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# 2. Data Models (Keep properties for serialization / reflection while obfuscating logic)
-keep class com.iaseasyway.academy.model.** { *; }

# 3. Strip All Debug Logging in Release Builds
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}

# 4. Kotlin Metadata & Coroutines
-keepattributes *Annotation*,InnerClasses,Signature,EnclosingMethod
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# 5. Anti-Reverse Engineering & Class Name Obfuscation
-repackageclasses 'com.iaseasyway.academy.core.internal'
-allowaccessmodification
-dontusemixedcaseclassnames
