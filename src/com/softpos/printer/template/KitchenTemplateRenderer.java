package com.softpos.printer.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Turns a kitchen ticket template into the row strings PrinterDriverControl.addTextIFont() expects
 * (e.g. {@code align=left><font face=Tahoma size=5>text}).
 *
 * <pre>
 * Line tags (any order, at the start of a line):  [l] [c] [r]  align,  [s1]..[s7] font size,  [b] bold
 * Blocks (each marker on its own line):
 *   {#items} ... {/items}        repeated for every item
 *   {#options} ... {/options}    inside items, repeated for every non-empty option of the item
 * Variables: {name}. Unknown variables are left as typed so a typo is visible on the ticket.
 * </pre>
 */
public final class KitchenTemplateRenderer {

    /** One order line. */
    public static class Item {
        public final Map<String, String> vars = new HashMap<>();
        public final List<String> options = new ArrayList<>();
    }

    private static final Pattern VAR = Pattern.compile("\\{([a-z_0-9]+)\\}");
    private static final Pattern TAG = Pattern.compile("^\\[(l|c|r|b|s[1-7])\\]");
    private static final String HR = "----------------------------------------";

    private KitchenTemplateRenderer() {
    }

    public static List<String> render(String template, Map<String, String> vars, List<Item> items) {
        List<String> out = new ArrayList<>();
        String[] lines = (template == null ? "" : template).split("\r?\n", -1);
        Map<String, String> base = new HashMap<>(vars);
        base.put("hr", HR);
        process(lines, 0, lines.length, base, null, items, out);
        return out;
    }

    private static void process(String[] lines, int from, int to, Map<String, String> vars,
            Item item, List<Item> items, List<String> out) {
        int i = from;
        while (i < to) {
            String marker = lines[i].trim();
            if (marker.equals("{#items}") && item == null) {
                int end = find(lines, i + 1, to, "{/items}");
                for (Item it : items) {
                    Map<String, String> v = new HashMap<>(vars);
                    v.putAll(it.vars);
                    process(lines, i + 1, end, v, it, items, out);
                }
                i = end + 1;
            } else if (marker.equals("{#options}") && item != null) {
                int end = find(lines, i + 1, to, "{/options}");
                for (String opt : item.options) {
                    Map<String, String> v = new HashMap<>(vars);
                    v.put("opt", opt);
                    process(lines, i + 1, end, v, item, items, out);
                }
                i = end + 1;
            } else {
                out.add(row(lines[i], vars));
                i++;
            }
        }
    }

    /** Index of the closing marker, or {@code to} when the template forgot it (block runs to the end). */
    private static int find(String[] lines, int from, int to, String marker) {
        for (int i = from; i < to; i++) {
            if (lines[i].trim().equals(marker)) {
                return i;
            }
        }
        return to;
    }

    private static String row(String line, Map<String, String> vars) {
        String align = "left";
        int size = 4;
        boolean bold = false;
        String rest = line;
        Matcher m;
        while ((m = TAG.matcher(rest)).find()) {
            String tag = m.group(1);
            switch (tag.charAt(0)) {
                case 'l':
                    align = "left";
                    break;
                case 'c':
                    align = "center";
                    break;
                case 'r':
                    align = "right";
                    break;
                case 'b':
                    bold = true;
                    break;
                default:
                    size = tag.charAt(1) - '0';
            }
            rest = rest.substring(m.end());
        }
        String text = escapeSpaces(substitute(rest, vars));
        if (text.isEmpty()) {
            text = "&nbsp;";
        }
        return "align=" + align + "><font face=Tahoma size=" + size + ">"
                + (bold ? "<b>" + text + "</b>" : text);
    }

    private static String substitute(String s, Map<String, String> vars) {
        Matcher m = VAR.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String v = vars.get(m.group(1));
            String rep = v == null ? m.group(0) : escapeHtml(v);
            m.appendReplacement(sb, Matcher.quoteReplacement(rep));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String escapeHtml(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    /** HTML collapses runs of spaces; keep the indentation the designer typed. */
    private static String escapeSpaces(String s) {
        StringBuilder sb = new StringBuilder();
        boolean prevSpace = true; // leading space must survive too
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                sb.append(prevSpace ? "&nbsp;" : " ");
                prevSpace = true;
            } else {
                sb.append(c);
                prevSpace = false;
            }
        }
        return sb.toString();
    }
}
