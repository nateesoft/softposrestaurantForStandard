package com.softpos.util;

/**
 * Lao text in latin1 database columns, used by ThaiUtil.
 *
 * Thai is stored one byte per letter (TIS-620, 0xA1-0xFB), leaving too few free bytes for Lao,
 * so each Lao letter U+0E80-U+0EFF (n = code - 0xE80, 0..127) is stored as two chars:
 * a lead 0xFC-0xFF (0xFC + n / 32) and a trail TRAILS.charAt(n % 32).
 * 0xFC-0xFF are unused by TIS-620 and survive Connector/J reads, unlike 0x81/0x8D/0x8F/0x90/0x9D
 * which come back as U+FFFD. Trails are letters and digits only, so encoded text is safe in SQL
 * built by concatenation (no \ or ') and in LIKE patterns (no _ or %).
 * A Lao letter therefore takes two characters of the column length.
 */
public class LaoUtil {

    private static final char LAO_FIRST = '\u0E80';
    private static final char LAO_LAST = '\u0EFF';
    private static final char LEAD_FIRST = '\u00FC';
    private static final char LEAD_LAST = '\u00FF';
    private static final String TRAILS = "0123456789ABCDEFGHIJKLMNOPQRSTUV";

    private LaoUtil() {
    }

    /** Lao letters to lead/trail pairs; everything else unchanged. */
    public static String encode(String text) {
        if (text == null || !hasLao(text)) {
            return text;
        }
        StringBuilder sb = new StringBuilder(text.length() * 2);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= LAO_FIRST && c <= LAO_LAST) {
                int n = c - LAO_FIRST;
                sb.append((char) (LEAD_FIRST + (n >> 5))).append(TRAILS.charAt(n & 0x1F));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /** Lead/trail pairs back to Lao letters; a lead without a valid trail is left as it is. */
    public static String decode(String text) {
        if (text == null || !hasLead(text)) {
            return text;
        }
        StringBuilder sb = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= LEAD_FIRST && c <= LEAD_LAST && i + 1 < text.length()) {
                int trail = TRAILS.indexOf(text.charAt(i + 1));
                if (trail >= 0) {
                    sb.append((char) (LAO_FIRST + ((c - LEAD_FIRST) << 5) + trail));
                    i++;
                    continue;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static boolean hasLao(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= LAO_FIRST && c <= LAO_LAST) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasLead(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= LEAD_FIRST && c <= LEAD_LAST) {
                return true;
            }
        }
        return false;
    }
}
