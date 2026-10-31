package com.softpos.i18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Developer tool (run from the IDE, working directory = project root) for ScreenTranslator:
 * - Thai designer texts of every src/**.form into src/com/softpos/i18n/screen_{lang}.properties
 * - Thai string literals of messages in src/**.java (inside MSG.xxx(...), JOptionPane.showXxx(...)
 *   and msg... = ...; statements) into src/com/softpos/i18n/message_{lang}.properties
 * Existing translations are kept; new texts get an empty value (= still Thai on screen).
 * Re-run after adding or changing screens or messages.
 */
public class ExtractScreenTexts {

    private static final String SRC = "src";
    private static final String OUT_DIR = "src/com/softpos/i18n";
    private static final String[] LANGUAGES = {"lo", "en"};

    // Start of a statement whose string literals are shown to the user as a message
    private static final Pattern MESSAGE_START = Pattern.compile(
            "\\bMSG\\s*\\.\\s*[A-Z]+\\s*\\(|\\bJOptionPane\\s*\\.\\s*show\\w*\\s*\\(|\\b\\w*[mM]sg\\w*\\s*\\+?=(?!=)");

    private static final List<String> TEXT_PROPERTIES = Arrays.asList("text", "title", "toolTipText", "tabTitle");
    // Text in these is user data, not a caption; ScreenTranslator does not touch it either
    private static final String[] DATA_COMPONENTS = {
        "TextField", "TextArea", "TextPane", "EditorPane", "PasswordField", "ComboBox", "Spinner", "JList"
    };

    public static void main(String[] args) throws Exception {
        List<File> forms = new ArrayList<>();
        findFiles(new File(SRC), ".form", forms);
        forms.sort((a, b) -> a.getPath().compareTo(b.getPath()));

        // text -> form that first uses it
        Map<String, String> texts = new LinkedHashMap<>();
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        for (File form : forms) {
            String formName = form.getPath().substring(SRC.length() + 1).replace(File.separatorChar, '/');
            for (String text : formTexts(db.parse(form).getDocumentElement())) {
                texts.putIfAbsent(text, formName);
            }
        }
        List<File> sources = new ArrayList<>();
        findFiles(new File(SRC), ".java", sources);
        sources.sort((a, b) -> a.getPath().compareTo(b.getPath()));
        Map<String, String> messages = new LinkedHashMap<>();
        for (File source : sources) {
            String sourceName = source.getPath().substring(SRC.length() + 1).replace(File.separatorChar, '/');
            String code = new String(Files.readAllBytes(source.toPath()), StandardCharsets.UTF_8);
            for (String text : messageTexts(code)) {
                messages.putIfAbsent(text, sourceName);
            }
        }

        for (String lang : LANGUAGES) {
            write(new File(OUT_DIR, "screen_" + lang + ".properties"), lang, texts,
                    "Screen translation", "Thai text from the screen designer");
            write(new File(OUT_DIR, "message_" + lang + ".properties"), lang, messages,
                    "Message translation", "Thai text (or a Thai part of a longer message) from MSG/JOptionPane in code");
        }
        System.out.println(forms.size() + " forms, " + texts.size() + " texts; "
                + sources.size() + " sources, " + messages.size() + " message texts");
    }

    private static void findFiles(File dir, String extension, List<File> out) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                findFiles(f, extension, out);
            } else if (f.getName().endsWith(extension)) {
                out.add(f);
            }
        }
    }

    /** Thai string literals in message statements, from the start match to the closing ")" or ";". */
    private static List<String> messageTexts(String code) {
        List<String> out = new ArrayList<>();
        Matcher m = MESSAGE_START.matcher(code);
        int pos = 0;
        while (pos < code.length() && m.find(pos)) {
            int depth = 0;
            int i = m.end();
            boolean call = code.charAt(i - 1) == '(';
            while (i < code.length()) {
                char c = code.charAt(i);
                if (c == '"') {
                    StringBuilder lit = new StringBuilder();
                    i = readLiteral(code, i + 1, lit);
                    String text = lit.toString().trim();
                    if (ScreenTranslator.hasThai(text) && !out.contains(text)) {
                        out.add(text);
                    }
                    continue;
                } else if (c == '\'') {
                    i = readLiteral(code, i + 1, new StringBuilder(), '\'');
                    continue;
                } else if (code.startsWith("//", i)) {
                    i = code.indexOf('\n', i);
                    if (i < 0) {
                        i = code.length();
                    }
                    continue;
                } else if (code.startsWith("/*", i)) {
                    i = code.indexOf("*/", i);
                    i = i < 0 ? code.length() : i + 2;
                    continue;
                } else if (c == '(') {
                    depth++;
                } else if (c == ')') {
                    if (call && depth == 0) {
                        break;
                    }
                    depth--;
                } else if (c == ';' || c == '{' || c == '}') {
                    break;
                }
                i++;
            }
            pos = Math.max(i, m.end());
        }
        return out;
    }

    private static int readLiteral(String code, int i, StringBuilder out) {
        return readLiteral(code, i, out, '"');
    }

    /** Reads a Java literal starting after its opening quote; returns the index after the closing quote. */
    private static int readLiteral(String code, int i, StringBuilder out, char quote) {
        while (i < code.length()) {
            char c = code.charAt(i++);
            if (c == quote || c == '\n') {
                break;
            }
            if (c != '\\' || i >= code.length()) {
                out.append(c);
                continue;
            }
            char e = code.charAt(i++);
            switch (e) {
                case 'n':
                    out.append('\n');
                    break;
                case 't':
                    out.append('\t');
                    break;
                case 'r':
                    out.append('\r');
                    break;
                case 'u':
                    while (i < code.length() && code.charAt(i) == 'u') {
                        i++;
                    }
                    out.append((char) Integer.parseInt(code.substring(i, i + 4), 16));
                    i += 4;
                    break;
                default:
                    out.append(e);
            }
        }
        return i;
    }

    private static List<String> formTexts(Element root) {
        List<String> out = new ArrayList<>();
        NodeList all = root.getElementsByTagName("*");
        for (int i = 0; i < all.getLength(); i++) {
            Element e = (Element) all.item(i);
            String text = null;
            switch (e.getTagName()) {
                case "Property":
                    if (TEXT_PROPERTIES.contains(e.getAttribute("name")) && e.hasAttribute("value")
                            && !isDataComponent(owner(e))) {
                        text = e.getAttribute("value");
                    }
                    break;
                case "TitledBorder":
                case "Column":
                    text = e.getAttribute("title");
                    break;
                default:
            }
            if (text != null && ScreenTranslator.hasThai(text) && !out.contains(text.trim())) {
                out.add(text.trim());
            }
        }
        return out;
    }

    // Property -> Properties -> Component/Container/Menu...
    private static String owner(Element property) {
        Node n = property.getParentNode();
        n = n == null ? null : n.getParentNode();
        return n instanceof Element ? ((Element) n).getAttribute("class") : "";
    }

    private static boolean isDataComponent(String cls) {
        for (String s : DATA_COMPONENTS) {
            if (cls.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private static void write(File file, String lang, Map<String, String> texts, String title, String keyDescription)
            throws IOException {
        Properties old = new Properties();
        if (file.exists()) {
            try (Reader r = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                old.load(r);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(title).append(" (").append(lang).append(", UTF-8) for ScreenTranslator\n");
        sb.append("# key = ").append(keyDescription).append(", value = translation; empty value = still Thai\n");
        sb.append("# Generated by com.softpos.i18n.ExtractScreenTexts - re-run it after changing screens,\n");
        sb.append("# existing translations are kept\n");
        String section = null;
        for (Map.Entry<String, String> t : texts.entrySet()) {
            if (!t.getValue().equals(section)) {
                section = t.getValue();
                sb.append("\n# ===== ").append(section).append(" =====\n");
            }
            appendEntry(sb, t.getKey(), old.getProperty(t.getKey(), ""));
        }
        boolean header = false;
        for (String key : old.stringPropertyNames()) {
            if (!texts.containsKey(key) && !old.getProperty(key).isEmpty()) {
                if (!header) {
                    sb.append("\n# ===== no longer on any screen (kept, can be deleted) =====\n");
                    header = true;
                }
                appendEntry(sb, key, old.getProperty(key));
            }
        }
        try (Writer w = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            w.write(sb.toString());
        }
    }

    private static void appendEntry(StringBuilder sb, String key, String value) {
        sb.append(escape(key, true)).append('=').append(escape(value, false)).append('\n');
    }

    private static String escape(String s, boolean isKey) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case ' ':
                    sb.append(isKey || i == 0 ? "\\ " : " ");
                    break;
                case '=':
                case ':':
                case '#':
                case '!':
                    if (isKey) {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}
