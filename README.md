# Gothic Legion Icons (Icon Pack + Widgets)

باك أيقونات (28 أيقونة) + 3 ويدجت (ساعة / تاريخ / بطارية) للتابلت، لانشر مايكروسوفت.

## بناء الـ APK من غير ما تنزّل أي أدوات (GitHub)
1. اعمل حساب على github.com وافتح Repository جديد (Private عادي).
2. ارفع كل محتويات الفولدر ده (بما فيها فولدر `.github`).
3. افتح تبويب **Actions** وستلاقي Build APK شغال تلقائيًا (أو شغله بزر Run workflow).
4. لما يخلص (3-6 دقايق) افتح التشغيل وحمّل **GothicLegion-APK** من خانة Artifacts.
5. فك الضغط وثبّت `app-debug.apk` على التاب (فعّل "تثبيت من مصادر غير معروفة").

## البناء من Android Studio
افتح الفولدر ← Build ← Build APK(s).

## التطبيق على مايكروسوفت لانشر
الإعدادات ← التخصيص ← حزمة الأيقونات ← Gothic Legion Icons.
افتح تطبيق Gothic Legion Icons لتشوف أنهي تطبيقات على التاب اتطابقت مع أنهي أيقونة.

## ملاحظات
- ربط الأيقونة بالتطبيق في `app/src/main/res/xml/appfilter.xml` (مكونات Google وSamsung وغيرها).
  تطبيقات Eden وTachiyomi وWuthering Waves والألعاب ممكن تحتاج اختيار يدوي من اللانشر.
- أسماء المطابقة بالاسم في `IconRules.java` لو عايز تضيف تطبيق.
