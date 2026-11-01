package com.gothiclegion.iconpack;

import java.util.Locale;

/** Maps an installed app's label to the icon name in this pack. */
final class IconRules {

    /** {iconName, displayName, exactLabels (|), containsLabels (|)} */
    static final String[][] RULES = {
        {"calendar", "Calendar", "calendar|google calendar|samsung calendar|التقويم|تقويم", ""},
        {"camera", "Camera", "camera|google camera|samsung camera|الكاميرا|كاميرا", ""},
        {"chrome", "Chrome", "chrome|google chrome|كروم", ""},
        {"clock", "Clock", "clock|google clock|الساعة|ساعة", ""},
        {"discord", "Discord", "", "discord"},
        {"drive", "Drive", "drive|google drive|درايف", ""},
        {"eden", "Eden", "eden|eden emulator", ""},
        {"facebook", "Facebook", "فيسبوك|فيس بوك", "facebook"},
        {"files", "Files", "files|my files|files by google|file manager|الملفات|ملفاتي|ملفات", ""},
        {"gallery", "Gallery", "gallery|المعرض|معرض الصور|معرض", ""},
        {"games", "Games", "games|game launcher|play games|google play games|الألعاب|العاب", ""},
        {"gmail", "Gmail", "", "gmail"},
        {"google", "Google", "google|جوجل|غوغل", ""},
        {"instagram", "Instagram", "", "instagram"},
        {"music", "Music", "music|youtube music|samsung music|play music|الموسيقى|موسيقى", ""},
        {"netflix", "Netflix", "", "netflix"},
        {"photos", "Photos", "photos|google photos|الصور|صور", ""},
        {"play_store", "Play Store", "play store|google play store|متجر play|متجر google play", ""},
        {"reddit", "Reddit", "", "reddit"},
        {"settings", "Settings", "settings|الإعدادات|الاعدادات|إعدادات|اعدادات", ""},
        {"spotify", "Spotify", "", "spotify"},
        {"tachiyomi", "Tachiyomi", "", "tachiyomi|mihon"},
        {"telegram", "Telegram", "", "telegram"},
        {"tiktok", "TikTok", "تيك توك", "tiktok"},
        {"whatsapp", "WhatsApp", "", "whatsapp"},
        {"wuthering_waves", "Wuthering Waves", "", "wuthering"},
        {"x", "X", "x|twitter|تويتر", ""},
        {"youtube", "YouTube", "youtube|يوتيوب", ""},
    };

    private IconRules() {}

    /** Returns the rule index matching this app label, or -1. */
    static int match(String label) {
        if (label == null) return -1;
        String l = label.trim().toLowerCase(Locale.ROOT);
        if (l.isEmpty()) return -1;
        for (int i = 0; i < RULES.length; i++) {
            if (hasAny(RULES[i][2], l, true) || hasAny(RULES[i][3], l, false)) return i;
        }
        return -1;
    }

    private static boolean hasAny(String list, String label, boolean exact) {
        if (list.isEmpty()) return false;
        for (String k : list.split("\\|")) {
            if (k.isEmpty()) continue;
            if (exact ? label.equals(k) : label.contains(k)) return true;
        }
        return false;
    }
}
