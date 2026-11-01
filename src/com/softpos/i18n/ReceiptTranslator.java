package com.softpos.i18n;

import com.softpos.util.AppLogUtil;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Language of printed receipts (driver/HTML printing).
 *
 * The print code builds receipts from English labels, turned Thai by PPrint.changeLanguage for
 * the "TH" receipt language. On non-Thai installations (connect.ini language) the finished HTML
 * is translated once more with com/softpos/i18n/receipt_{lang}.properties, whose keys are those
 * Thai and English labels: the "TH" receipt button prints in the shop language, "EN" in English.
 * Only text between HTML tags is translated, longest key first; English keys only match whole
 * words. Thai installations print exactly as before.
 * Reports and kitchen tickets always use the shop language ("TH").
 */
public class ReceiptTranslator {

    private static final String BUNDLE = "com.softpos.i18n.receipt";
    private static final Pattern TEXT = Pattern.compile(">([^<]+)");
    // Unquoted "face=Angsana New size=2" is read by Swing as face=Angsana, so it never was Angsana New
    private static final Pattern FONT_FACE = Pattern.compile("face=(\"[^\"]*\"|'[^']*'|sAngsana New|Angsana New|Tahoma)");

    private static final Pattern SMALL_THAI_FONT = Pattern.compile("(?i)sarabun|angsana|cordia|browallia");
    private static final float SMALL_THAI_FONT_SCALE = 0.7f;

    private static final Map<Locale, List<Entry>> ENTRIES = new HashMap<>();

    private ReceiptTranslator() {
    }

    private static class Entry {

        final Pattern key;
        final String value;

        Entry(Pattern key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /** Receipt locale for the given PublicVar.languagePrint ("TH"/"EN"); null = print unchanged. */
    public static Locale receiptLocale(String languagePrint) {
        if (Lang.THAI.getLanguage().equals(Lang.getLocale().getLanguage())) {
            return null;
        }
        return "EN".equalsIgnoreCase(languagePrint) ? Locale.ENGLISH : Lang.getLocale();
    }

    /** The receipt HTML in the receipt language, with a font that has Lao glyphs for Lao. */
    public static String translateHtml(String html, String languagePrint) {
        Locale locale = receiptLocale(languagePrint);
        if (html == null || locale == null) {
            return html;
        }
        List<Entry> entries = entries(locale);
        Matcher m = TEXT.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, Matcher.quoteReplacement(">" + translate(m.group(1), entries)));
        }
        m.appendTail(sb);
        String result = sb.toString();
        if (Lang.LAO.getLanguage().equals(locale.getLanguage())) {
            String face = "face=\"" + Lang.getThaiLaoFontFamily() + "\"";
            result = FONT_FACE.matcher(result).replaceAll(Matcher.quoteReplacement(face));
            result = result.replace("<body>", "<body style=\"font-family:'" + Lang.getThaiLaoFontFamily() + "'\">");
        }
        return result;
    }

    /** Plain printed text (kitchen tickets drawn with Graphics, Jasper texts) in the shop language. */
    public static String translateText(String text) {
        Locale locale = receiptLocale("TH");
        return text == null || locale == null ? text : translate(text, entries(locale));
    }

    /** Font for printed text: as requested on Thai installations, a font with Lao glyphs for Lao. */
    public static Font printFont(String family, int style, int size) {
        return isLaoPrint() ? Lang.createScreenFont(Lang.getThaiLaoFontFamily(), style, size) : new Font(family, style, size);
    }

    /** Font family for printed text, see printFont. */
    public static String printFontFamily(String family) {
        return isLaoPrint() ? Lang.getThaiLaoFontFamily() : family;
    }

    /**
     * Font size for printFontFamily(family): Thai fonts like TH Sarabun New and Angsana New are
     * small for their point size, so the Lao font is scaled down to fit the same layout.
     */
    public static int printFontSize(String family, int size) {
        if (isLaoPrint() && family != null && SMALL_THAI_FONT.matcher(family).find()) {
            return Math.round(size * SMALL_THAI_FONT_SCALE);
        }
        return size;
    }

    private static boolean isLaoPrint() {
        Locale locale = receiptLocale("TH");
        return locale != null && Lang.LAO.getLanguage().equals(locale.getLanguage());
    }

    private static String translate(String text, List<Entry> entries) {
        String result = text;
        for (Entry e : entries) {
            result = e.key.matcher(result).replaceAll(e.value);
        }
        return result;
    }

    private static synchronized List<Entry> entries(Locale locale) {
        return ENTRIES.computeIfAbsent(locale, l -> {
            List<Entry> list = new ArrayList<>();
            ResourceBundle b;
            try {
                b = ResourceBundle.getBundle(BUNDLE, l, new Lang.UTF8Control());
            } catch (MissingResourceException e) {
                AppLogUtil.info(ReceiptTranslator.class, "No receipt translation for language '" + l + "'");
                return list;
            }
            List<String> keys = new ArrayList<>(b.keySet());
            keys.sort((x, y) -> y.length() - x.length());
            for (String key : keys) {
                String value = b.getString(key);
                if (!value.isEmpty()) {
                    list.add(new Entry(keyPattern(key), Matcher.quoteReplacement(value)));
                }
            }
            return list;
        });
    }

    // Keys match whole words only: "Item" not inside "Itemize", "ที่" not inside "ขอบคุณที่ใช้บริการ"
    // (labels in the print code are separated by spaces or punctuation)
    private static Pattern keyPattern(String key) {
        String regex = Pattern.quote(key);
        String start = wordClass(key.charAt(0));
        String end = wordClass(key.charAt(key.length() - 1));
        if (start != null) {
            regex = "(?<!" + start + ")" + regex;
        }
        if (end != null) {
            regex = regex + "(?!" + end + ")";
        }
        return Pattern.compile(regex);
    }

    private static String wordClass(char c) {
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
            return "[A-Za-z0-9]";
        }
        if (c >= '\u0E01' && c <= '\u0E4E') {
            return "[\u0E01-\u0E4E]";
        }
        return null;
    }
}
