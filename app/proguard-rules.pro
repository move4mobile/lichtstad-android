# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# The default of 5 is massive overkill
# Hardly anything changes after the second optimization pass
-optimizationpasses 2

#Picasso
-dontwarn com.squareup.okhttp.**

#Firebase database
-keepattributes Signature
-keepclassmembers public class com.move4mobile.lichtstad.model.** {
    *;
}

##Crashlytics
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

#BottomNavigationViewTinter
-keep class com.google.android.material.bottomnavigation.** {
    *;
}
-keepclassmembernames class com.google.android.material.bottomnavigation.** {
    *;
}
-keep class com.google.android.material.navigation.NavigationBarItemView {
    *;
}
-keepclassmembernames class com.google.android.material.navigation.NavigationBarItemView {
    *;
}

#Moshi
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepclasseswithmembers,allowobfuscation class * {
    @com.squareup.moshi.* <methods>;
    @com.squareup.moshi.* <fields>;
}
-keep @com.squareup.moshi.JsonQualifier interface *

#As long as we don't use architecture components, we probably shouldn't care about some classes missing
-dontwarn android.arch.**

#Android common
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
