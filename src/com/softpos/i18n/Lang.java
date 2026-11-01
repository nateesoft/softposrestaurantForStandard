package com.softpos.i18n;

import com.softpos.connection.database.ConfigFile;
import com.softpos.util.AppLogUtil;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.plaf.FontUIResource;

/**
 * Screen-text language.
 *
 * Texts live in com/softpos/i18n/messages_{lang}.properties (UTF-8).
 * Selected by "language,{lang}" in connect.ini (e.g. th, lo, en, en_US); if no file exists
 * for that language, or the setting is empty, Thai is used. Missing keys also fall back to Thai.
 * Receipt language is separate (languagePrint / PublicVar.languagePrint).
 */
public class Lang {

    public static final Locale THAI = new Locale("th");
    public static final Locale LAO = new Locale("lo");

    private static final String BUNDLE = "com.softpos.i18n.messages";
    private static final String CONFIG_KEY = "language";
    private static final String FONT_DIR = "fonts";

    // Tahoma has no Lao glyphs, so Lao screens need a font that renders both Thai and Lao
    private static final String[] THAI_LAO_FONTS = {
        "Leelawadee UI", "Leelawadee", "Noto Sans Lao", "Phetsarath OT", "Saysettha OT", "Lao Sangam MN"
    };

    private static Locale locale = readConfigLocale();
    private static ResourceBundle bundle = load(locale);
    private static ResourceBundle fallback = load(THAI);
    private static String thaiLaoFontFamily;

    private Lang() {
    }

    private static Locale readConfigLocale() {
        return toLocale(readConfigValue(CONFIG_KEY));
    }

    /** "en", "en_US" or "en-US" to a Locale; empty or invalid means Thai. */
    public static Locale toLocale(String code) {
        if (code == null || code.trim().isEmpty()) {
            return THAI;
        }
        Locale l = Locale.forLanguageTag(code.trim().replace('_', '-'));
        return l.getLanguage().isEmpty() ? THAI : l;
    }

    // Exact key match: ConfigFile.getProperties() uses contains(), which would also hit "languagePrint"
    private static String readConfigValue(String key) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(ConfigFile.FILE_CONFIG), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", 2);
                if (data.length == 2 && data[0].trim().equalsIgnoreCase(key)) {
                    return data[1].trim();
                }
            }
        } catch (IOException e) {
            AppLogUtil.log(Lang.class, "error", e);
        }
        return "";
    }

    private static ResourceBundle load(Locale l) {
        ResourceBundle b = ResourceBundle.getBundle(BUNDLE, l, new UTF8Control());
        if (!b.getLocale().getLanguage().equals(l.getLanguage())) {
            AppLogUtil.info(Lang.class, "No screen texts for language '" + l + "', using " + b.getLocale());
        }
        return b;
    }

    public static Locale getLocale() {
        return locale;
    }

    public static boolean isLao() {
        return LAO.getLanguage().equals(locale.getLanguage());
    }

    /** Switch language at runtime; open screens must re-apply their texts. */
    public static void setLocale(Locale newLocale) {
        locale = newLocale == null ? THAI : newLocale;
        bundle = load(locale);
    }

    /**
     * Text for key in the current language, falling back to Thai, then to the key itself.
     * With args, the text is a MessageFormat pattern ({0}, {1}, ...).
     */
    public static String get(String key, Object... args) {
        String text;
        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            try {
                text = fallback.getString(key);
            } catch (MissingResourceException e2) {
                return key;
            }
        }
        return args.length == 0 ? text : MessageFormat.format(text, args);
    }

    /**
     * Replace fonts on the given components and all their children (including menu items) with
     * screen fonts (see screenFont), keeping style and size. Thai keeps each component's family
     * (e.g. Tahoma); Lao switches to a font with Lao glyphs.
     */
    public static void applyFont(Component... comps) {
        for (Component c : comps) {
            applyFontTree(c);
        }
    }

    private static void applyFontTree(Component c) {
        if (c == null) {
            return;
        }
        Font old = c.getFont();
        // FontUIResource means already wrapped here, or a look-and-feel default that has fallback
        if (old != null && (!(old instanceof FontUIResource) || !screenFamily(old.getFamily()).equals(old.getFamily()))) {
            c.setFont(screenFont(old.getFamily(), old.getStyle(), old.getSize()));
        }
        if (c instanceof JMenu) {
            for (Component item : ((JMenu) c).getMenuComponents()) {
                applyFontTree(item);
            }
        }
        if (c instanceof JComponent && ((JComponent) c).getComponentPopupMenu() != null) {
            applyFontTree(((JComponent) c).getComponentPopupMenu());
        }
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                applyFontTree(child);
            }
        }
    }

    /**
     * Font for anything set after Lang.applyFont (e.g. product buttons whose font comes from
     * the database): the requested family in Thai/other languages, the Lao font in Lao, and in
     * both cases glyphs the font lacks (Lao names in Tahoma, Thai names in a Lao font) fall
     * back to system fonts instead of showing boxes.
     */
    public static Font screenFont(String family, int style, int size) {
        return createScreenFont(screenFamily(family), style, size);
    }

    private static String screenFamily(String family) {
        return isLao() ? getThaiLaoFontFamily() : family;
    }

    /**
     * Font family for Lao screens. A Lao font in the ./fonts folder (next to connect.ini) wins,
     * so client machines need no installed Lao font; Thai glyphs it lacks come from the system
     * fallback (see createScreenFont). Otherwise an installed font with both Thai and Lao is used.
     */
    public static synchronized String getThaiLaoFontFamily() {
        if (thaiLaoFontFamily == null) {
            List<String> folderFamilies = registerFolderFonts();
            // one file per weight gives "Noto Sans Lao", "Noto Sans Lao Black", ...; the base name is shortest
            folderFamilies.sort(Comparator.comparingInt(String::length));
            thaiLaoFontFamily = firstFontAbleToDisplay(folderFamilies, "ຊ");
            if (thaiLaoFontFamily == null) {
                thaiLaoFontFamily = firstFontAbleToDisplay(Arrays.asList(THAI_LAO_FONTS), "กຊ");
            }
            if (thaiLaoFontFamily == null) {
                thaiLaoFontFamily = Font.DIALOG; // logical font falls back per script
            }
            AppLogUtil.info(Lang.class, "Lao screen font: " + thaiLaoFontFamily);
        }
        return thaiLaoFontFamily;
    }

    /**
     * The given font wrapped so glyphs it lacks (Thai text in a Lao-only font, Lao text in
     * Tahoma) fall back to the system fonts instead of showing boxes.
     */
    public static Font createScreenFont(String family, int style, int size) {
        Font font = new Font(family, style, size);
        try {
            // Java 8 internal API; same fallback Swing look-and-feels use for their own fonts
            Class<?> utils = Class.forName("sun.font.FontUtilities");
            return (Font) utils.getMethod("getCompositeFontUIResource", Font.class).invoke(null, font);
        } catch (ReflectiveOperationException | RuntimeException | LinkageError e) {
            return font;
        }
    }

    private static String firstFontAbleToDisplay(List<String> families, String sample) {
        for (String name : families) {
            Font f = new Font(name, Font.PLAIN, 12);
            if (f.getFamily().equalsIgnoreCase(name) && f.canDisplayUpTo(sample) == -1) {
                return name;
            }
        }
        return null;
    }

    /** Registers every .ttf/.otf in FONT_DIR with Java and returns their family names. */
    private static List<String> registerFolderFonts() {
        List<String> families = new ArrayList<>();
        File[] files = new File(FONT_DIR).listFiles((dir, name) -> {
            String n = name.toLowerCase();
            return n.endsWith(".ttf") || n.endsWith(".otf");
        });
        if (files == null) {
            return families;
        }
        Arrays.sort(files);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (File file : files) {
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, file);
                ge.registerFont(font); // false when the same font is already installed; it is still usable
                if (!families.contains(font.getFamily())) {
                    families.add(font.getFamily());
                }
            } catch (FontFormatException | IOException e) {
                AppLogUtil.log(Lang.class, "error", e);
            }
        }
        return families;
    }

    // Java 8 reads .properties as ISO-8859-1; read them as UTF-8 instead
    static class UTF8Control extends ResourceBundle.Control {

        @Override
        public List<String> getFormats(String baseName) {
            return Collections.singletonList("java.properties");
        }

        @Override
        public Locale getFallbackLocale(String baseName, Locale l) {
            return THAI.equals(l) ? null : THAI;
        }

        @Override
        public ResourceBundle newBundle(String baseName, Locale l, String format,
                ClassLoader loader, boolean reload) throws IOException {
            String resource = toResourceName(toBundleName(baseName, l), "properties");
            InputStream is = loader.getResourceAsStream(resource);
            if (is == null) {
                return null;
            }
            try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                return new PropertyResourceBundle(r);
            }
        }
    }
}
