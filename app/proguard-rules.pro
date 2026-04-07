# Keep kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.kolo.prayer.**$$serializer { *; }
-keepclassmembers class com.kolo.prayer.** { *** Companion; }
-keepclasseswithmembers class com.kolo.prayer.** { kotlinx.serialization.KSerializer serializer(...); }
