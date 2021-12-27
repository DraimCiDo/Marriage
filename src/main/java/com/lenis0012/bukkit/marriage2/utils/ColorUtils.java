package com.lenis0012.bukkit.marriage2.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.getLastColors;

public class ColorUtils
{

    public static String colorMessage(String message) {
        return RGBUtils.colorize(message);
    }

    /**
     * Formatter for &x&R&R&G&G&B&B
    */
    public static class BukkitFormat implements RGBFormatter {

        private final Pattern pattern = Pattern.compile("[\\\u00a7&]{1}x[[\\\u00a7&]{1}0-9a-fA-F]{12}");

        @Override
        public String reformat(String text) {
            if (!text.contains("&") && !text.contains("\u00a7x")) return text;
            String replaced = text;
            Matcher m = pattern.matcher(replaced);
            while (m.find()) {
                String hexcode = m.group();
                String fixed = new String(new char[] {'#', hexcode.charAt(3), hexcode.charAt(5), hexcode.charAt(7), hexcode.charAt(9), hexcode.charAt(11), hexcode.charAt(13)});
                replaced = replaced.replace(hexcode, fixed);
            }
            return replaced;
        }
    }

    /**
     * Formatter for {#RRGGBB}
     */
    public static class CMIFormat implements RGBFormatter {

        private final Pattern pattern = Pattern.compile("\\{#[0-9a-fA-F]{6}\\}");

        @Override
        public String reformat(String text) {
            if (!text.contains("{#")) return text;
            String replaced = text;
            Matcher m = pattern.matcher(replaced);
            while (m.find()) {
                String hexcode = m.group();
                String fixed = hexcode.substring(2, 8);
                replaced = replaced.replace(hexcode, "#" + fixed);
            }
            return replaced;
        }
    }

    /**
     * A class representing the n.m.s.EnumChatFormat class to make work with it much easier
     */
    public enum EnumChatFormat {

        BLACK(0, '0', "#000000"),
        DARK_BLUE(1, '1', "#0000AA"),
        DARK_GREEN(2, '2', "#00AA00"),
        DARK_AQUA(3, '3', "#00AAAA"),
        DARK_RED(4, '4', "#AA0000"),
        DARK_PURPLE(5, '5', "#AA00AA"),
        GOLD(6, '6', "#FFAA00"),
        GRAY(7, '7', "#AAAAAA"),
        DARK_GRAY(8, '8', "#555555"),
        BLUE(9, '9', "#5555FF"),
        GREEN(10, 'a', "#55FF55"),
        AQUA(11, 'b', "#55FFFF"),
        RED(12, 'c', "#FF5555"),
        LIGHT_PURPLE(13, 'd', "#FF55FF"),
        YELLOW(14, 'e', "#FFFF55"),
        WHITE(15, 'f', "#FFFFFF"),
        OBFUSCATED(16, 'k'),
        BOLD(17, 'l'),
        STRIKETHROUGH(18, 'm'),
        UNDERLINE(19, 'n'),
        ITALIC(20, 'o'),
        RESET(21, 'r');

        private static final EnumChatFormat[] values = EnumChatFormat.values();
        //network id of the color
        private int networkId;

        //characer representing the color
        private char character;

        //red value of this color
        private int red;

        //green value of this color
        private int green;

        //blue value of this color
        private int blue;

        //hex code as string prefixed with #
        private String hexCode;

        //\u00a7 followed by color's character
        private final String chatFormat;

        /**
         * Constructs new instance with given parameters
         * @param networkId - network id of the color
         * @param character - character representing the color
         * @param hexCode - hex code of the color
         */
        private EnumChatFormat(int networkId, char character, String hexCode) {
            this(networkId, character);
            this.hexCode = hexCode;
            int hexColor = Integer.parseInt(hexCode.substring(1), 16);
            red = (hexColor >> 16) & 0xFF;
            green = (hexColor >> 8) & 0xFF;
            blue = hexColor & 0xFF;
        }

        /**
         * Constructs new instance with given parameters
         * @param networkId - network id of the color
         * @param character - character representing the color
         */
        private EnumChatFormat(int networkId, char character) {
            this.networkId = networkId;
            this.character = character;
            this.chatFormat = "\u00a7" + character;
        }

        /**
         * Returns network id of this color
         * @return network if of this color
         */
        public int getNetworkId() {
            return networkId;
        }

        /**
         * Returns red value of this color
         * @return red value
         */
        public int getRed() {
            return red;
        }

        /**
         * Returns green value of this color
         * @return green value
         */
        public int getGreen() {
            return green;
        }

        /**
         * Returns blue value of this color
         * @return blue value
         */
        public int getBlue() {
            return blue;
        }

        /**
         * Returns enum value based on inserted color or null if character is not valid
         * @param c - color code character (0-9, a-f, k-o, r)
         * @return instance from the character
         */
        public static EnumChatFormat getByChar(char c) {
            for (EnumChatFormat format : values) {
                if (format.character == c) return format;
            }
            return null;
        }

        /**
         * Returns enum value of last colors used in given string
         * @param string - string to check last colors of
         * @return last used color code in given string
         */
        public static EnumChatFormat lastColorsOf(String string) {
            if (string == null || string.length() == 0) return EnumChatFormat.WHITE;
            String legacyText = RGBUtils.getInstance().convertRGBtoLegacy(string); //translating RGB into legacy for nametags
            String last = getLastColors(legacyText);
            if (last != null && last.length() > 0) {
                char c = last.toCharArray()[1];
                for (EnumChatFormat e : values) {
                    if (e.character == c) return e;
                }
            }
            return EnumChatFormat.WHITE;
        }

        //code taken from bukkit, so it can work on bungee too
        public static String getLastColors(String input) {
            String result = "";
            int length = input.length();
            for (int index = length - 1; index > -1; index--) {
                char section = input.charAt(index);
                if ((section == '\u00a7' || section == '&') && (index < length - 1)) {
                    char c = input.charAt(index + 1);
                    if ("0123456789AaBbCcDdEeFfKkLlMmNnOoRr".contains(String.valueOf(c))) {
                        result = "\u00a7" + c + result;
                        if ("0123456789AaBbCcDdEeFfRr".contains(String.valueOf(c))) {
                            break;
                        }
                    }
                }
            }
            return result;
        }

        /**
         * Returns \u00a7 followed by color's character
         * @return \u00a7 followed by color's character
         */
        public String getFormat() {
            return chatFormat;
        }

        /**
         * Returns character representing this color
         * @return character representing this color
         */
        public char getCharacter() {
            return character;
        }

        /**
         * Returns hex code of this format prefixed with #, null if this is a magic code
         * @return hex code of this format prefixed with #
         */
        public String getHexCode() {
            return hexCode;
        }

        /**
         * Returns enum value with exact red, green and blue values or null if no match
         * @param red - exact red value
         * @param green - exact green value
         * @param blue - exact blue value
         * @return enum value or null if no such combination exists
         */
        public static EnumChatFormat fromRGBExact(int red, int green, int blue) {
            for (EnumChatFormat format : values) {
                if (format.red == red && format.green == green && format.blue == blue) return format;
            }
            return null;
        }
    }

    /**
     * Formatter for #<RRGGBB>
     */
    public static class HtmlFormat implements RGBFormatter {

        private final Pattern pattern = Pattern.compile("#<[0-9a-fA-F]{6}>");

        @Override
        public String reformat(String text) {
            if (!text.contains("#<")) return text;
            Matcher m = pattern.matcher(text);
            String replaced = text;
            while (m.find()) {
                String hexcode = m.group();
                String fixed = hexcode.substring(2, 8);
                replaced = replaced.replace(hexcode, "#" + fixed);
            }
            return replaced;
        }
    }

    /**
     * Abstract class for different RGB patterns
     */
    public static interface RGBFormatter {

        /**
         * Reformats RGB codes in provided text into #RRGGBB format
         * @param text - text to format
         * @return reformatted text
         */
        public String reformat(String text);
    }

    /**
     * Formatter for &#RRGGBB
     */
    public static class UnnamedFormat1 implements RGBFormatter {

        @Override
        public String reformat(String text) {
            return text.contains("&#") ? text.replace("&#", "#") : text;
        }
    }

    public static class CommonGradient extends GradientPattern {

        private Pattern pattern;
        private Pattern legacyPattern;
        private String containCheck;
        private int legacyCharPosition;
        private int startColorStart;
        private int messageStart;
        private int endColorStartSub;

        public CommonGradient(Pattern pattern, Pattern legacyPattern, String containCheck, int legacyCharPosition,
                              int startColorStart, int messageStart, int endColorStartSub) {
            this.pattern = pattern;
            this.legacyPattern = legacyPattern;
            this.containCheck = containCheck;
            this.legacyCharPosition = legacyCharPosition;
            this.startColorStart = startColorStart;
            this.messageStart = messageStart;
            this.endColorStartSub = endColorStartSub;
        }

        @Override
        public String applyPattern(String text, boolean ignorePlaceholders) {
            if (!text.contains(containCheck)) return text;
            String replaced = text;
            Matcher m = legacyPattern.matcher(replaced);
            while (m.find()) {
                String format = m.group();
                EnumChatFormat legacyColor = EnumChatFormat.getByChar(format.charAt(legacyCharPosition));
                if ((ignorePlaceholders && format.contains("%")) || legacyColor == null) continue;
                TextColor start = new TextColor(format.substring(startColorStart, startColorStart+7), legacyColor);
                String message = format.substring(messageStart+2, format.length()-10);
                TextColor end = new TextColor(format.substring(format.length()-endColorStartSub, format.length()-endColorStartSub+7));
                String applied = asGradient(start, message, end);
                replaced = replaced.replace(format, applied);
            }
            m = pattern.matcher(replaced);
            while (m.find()) {
                String format = m.group();
                if (ignorePlaceholders && format.contains("%")) continue;
                TextColor start = new TextColor(format.substring(startColorStart, startColorStart+7));
                String message = format.substring(messageStart, format.length()-10);
                TextColor end = new TextColor(format.substring(format.length()-endColorStartSub, format.length()-endColorStartSub+7));
                String applied = asGradient(start, message, end);
                replaced = replaced.replace(format, applied);
            }
            return replaced;
        }
    }

    /**
     * Abstract class for applying different gradient patterns
     */
    public abstract static class GradientPattern {

        /**
         * Applies gradients in provided text and returns text using only #RRGGBB
         * @param text - text to be reformatted
         * @return reformatted text
         */
        public abstract String applyPattern(String text, boolean ignorePlaceholders);

        /**
         * Returns gradient text based on start color, text and end color
         * @param start - start color
         * @param text - text to be reformatted
         * @param end - end color
         * @return reformatted text
         */
        protected String asGradient(TextColor start, String text, TextColor end) {
            //lazy support for magic codes in gradients
            String magicCodes = getLastColors(text);
            String decolorized = text.substring(magicCodes.length());
            StringBuilder sb = new StringBuilder();
            int length = decolorized.length();
            for (int i=0; i<length; i++) {
                int red = (int) (start.getRed() + (float)(end.getRed() - start.getRed())/(length-1)*i);
                int green = (int) (start.getGreen() + (float)(end.getGreen() - start.getGreen())/(length-1)*i);
                int blue = (int) (start.getBlue() + (float)(end.getBlue() - start.getBlue())/(length-1)*i);
                sb.append(new TextColor(red, green, blue).getHexCode());
                if (start.isLegacyColorForced()) sb.append("|" + start.getLegacyColor().getCharacter());
                sb.append(magicCodes + decolorized.charAt(i));
            }
            return sb.toString();
        }
    }

    /**
     * A helper class to reformat all RGB formats into the default #RRGGBB and apply gradients
     */
    public static class RGBUtils {

        //instance of class
        private static RGBUtils instance = new RGBUtils();

        //list of rgb formatters
        private Set<RGBFormatter> formats = new HashSet<>();

        //list of gradient patterns
        private Set<GradientPattern> gradients = new HashSet<>();

        //TAB's RGB format
        private final Pattern tabPattern = Pattern.compile("#[0-9a-fA-F]{6}");
        private final Pattern tabPatternLegacy = Pattern.compile("#[0-9a-fA-F]{6}\\|.");

        public RGBUtils() {
            registerRGBFormatter(new BukkitFormat());
            registerRGBFormatter(new CMIFormat());
            registerRGBFormatter(new UnnamedFormat1());
            registerRGBFormatter(new HtmlFormat());

            //{#RRGGBB>}text{#RRGGBB<}
            registerGradient(new CommonGradient(Pattern.compile("\\{#[0-9a-fA-F]{6}>\\}[^\\{]*\\{#[0-9a-fA-F]{6}<\\}"),
                    Pattern.compile("\\{#[0-9a-fA-F]{6}\\|.>\\}[^\\{]*\\{#[0-9a-fA-F]{6}<\\}"),
                    "{#", 9, 1, 10, 9));

            //<#RRGGBB>Text</#RRGGBB>
            registerGradient(new CommonGradient(Pattern.compile("<#[0-9a-fA-F]{6}>[^<]*</#[0-9a-fA-F]{6}>"),
                    Pattern.compile("<#[0-9a-fA-F]{6}\\|.>[^<]*</#[0-9a-fA-F]{6}>"),
                    "<#", 9, 1, 9, 8));

            //<$#RRGGBB>Text<$#RRGGBB>
            registerGradient(new CommonGradient(Pattern.compile("<\\$#[0-9a-fA-F]{6}>[^<]*<\\$#[0-9a-fA-F]{6}>"),
                    Pattern.compile("<\\$#[0-9a-fA-F]{6}\\|.>[^<]*<\\$#[0-9a-fA-F]{6}>"),
                    "<$", 10, 2, 10, 8));
        }

        public static String colorize(String message) {
            return getInstance().color(getInstance().convertToBukkitFormat(getInstance().applyFormats(message, false)));
        }

        public static BaseComponent colorToComponent(String message) {
            return ComponentSerializer.parse(legacyToJson(getInstance().color(getInstance().convertToBukkitFormat(getInstance().applyFormats(message, false)))))[0];
        }

        public static String legacyToJson(String legacyString) {
            if (legacyString == null) return "";
            return ComponentSerializer.toString(TextComponent.fromLegacyText(legacyString));
        }


        public static String jsonToLegacy(String json) {
            if (json == null) return "";
            return BaseComponent.toLegacyText(ComponentSerializer.parse(json));
        }

        /**
         * Returns instance of this class
         * @return instance
         */
        public static RGBUtils getInstance() {
            return instance;
        }

        /**
         * Applies all RGB formats and gradients to text and returns it
         * @param text - original text
         * @return text where everything is converted to #RRGGBB
         */
        public String applyFormats(String text, boolean ignorePlaceholders) {
            String replaced = text;
            for (RGBFormatter formatter : formats) {
                replaced = formatter.reformat(replaced);
            }
            for (GradientPattern pattern : gradients) {
                replaced = pattern.applyPattern(replaced, ignorePlaceholders);
            }
            return replaced;
        }

        /**
         * Registers RGB formatter
         * @param formatter - formatter to register
         */
        public void registerRGBFormatter(RGBFormatter formatter) {
            formats.add(formatter);
        }

        /**
         * Registers gradient pattern
         * @param pattern - gradient pattern to register
         */
        public void registerGradient(GradientPattern pattern) {
            gradients.add(pattern);
        }

        /**
         * Converts all hex codes in given string to legacy codes
         * @param text - text to translate
         * @return - translated text
         */
        public String convertRGBtoLegacy(String text) {
            if (text == null) return null;
            if (!text.contains("#")) return color(text);
            String applied = applyFormats(text, false);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < applied.length(); i++) {
                char c = applied.charAt(i);
                if (c == '#') {
                    try {
                        if (containsLegacyCode(applied, i)) {
                            sb.append(new TextColor(applied.substring(i, i+7), EnumChatFormat.getByChar(applied.charAt(i+8))).getLegacyColor().getFormat());
                            i += 8;
                        } else {
                            sb.append(new TextColor(applied.substring(i, i+7)).getLegacyColor().getFormat());
                            i += 6;
                        }
                    } catch (Exception e) {
                        //not a valid RGB code
                        sb.append(c);
                    }
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        public String color(String textToTranslate) {
            if (textToTranslate == null) return null;
            if (!textToTranslate.contains("&")) return textToTranslate;
            char[] b = textToTranslate.toCharArray();
            for (int i = 0; i < b.length - 1; i++) {
                if ((b[i] == '&') && ("0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[(i + 1)]) > -1)) {
                    b[i] = '\u00a7';
                    b[(i + 1)] = Character.toLowerCase(b[(i + 1)]);
                }
            }
            return new String(b);
        }

        public String convertToBukkitFormat(String text) {
            //converting random formats to TAB one
            String replaced = applyFormats(text, false);
            Matcher m = tabPatternLegacy.matcher(replaced);
            while (m.find()) {
                String hexcode = m.group();
                String fixed = "&x&" + hexcode.charAt(1) + "&" + hexcode.charAt(2) + "&" + hexcode.charAt(3) + "&" + hexcode.charAt(4) + "&" + hexcode.charAt(5) + "&" + hexcode.charAt(6);
                replaced = replaced.replace(hexcode, fixed.replace('&', '\u00a7'));
            }
            m = tabPattern.matcher(replaced);
            while (m.find()) {
                String hexcode = m.group();
                String fixed = "&x&" + hexcode.charAt(1) + "&" + hexcode.charAt(2) + "&" + hexcode.charAt(3) + "&" + hexcode.charAt(4) + "&" + hexcode.charAt(5) + "&" + hexcode.charAt(6);
                replaced = replaced.replace(hexcode, fixed.replace('&', '\u00a7'));
            }
            return replaced;
        }

        /**
         * Returns true if text contains legacy color request at defined RGB index start
         * @param text - text to check
         * @param i - current index start
         * @return true if legacy color is defined, false if not
         */
        private boolean containsLegacyCode(String text, int i) {
            if (text.length() - i < 9 || text.charAt(i+7) != '|') return false;
            return EnumChatFormat.getByChar(text.charAt(i+8)) != null;
        }
    }

    /**
     * A class used to represent any combination of RGB colors
     */
    public static class TextColor {

        private static EnumChatFormat[] legacyColors = EnumChatFormat.values();

        //red value
        private Integer red;

        //green value
        private Integer green;

        //blue value
        private Integer blue;

        //closest legacy color
        private EnumChatFormat legacyColor;

        //hex code as string prefixed with #
        private String hexCode;

        //true if legacy color was forced via constructor, false if automatically
        private boolean legacyColorForced;

        //true if toString should return legacy color
        private boolean returnLegacy;

        /**
         * Constructs new instance based on hex code as string
         * @param hexCode - a 6-digit combination of hex numbers
         */
        public TextColor(String hexCode) {
            this.hexCode = hexCode;
        }

        /**
         * Constructs new instance from given 6-digit hex code and legacy color
         * @param hexCode - 6-digit hex code
         * @param legacyColor color to use for legacy clients
         */
        public TextColor(String hexCode, EnumChatFormat legacyColor) {
            this.hexCode = hexCode;
            this.legacyColorForced = true;
            this.legacyColor = legacyColor;
        }

        /**
         * Constructs new instance with given parameter
         * @param legacyColor - legacy color
         */
        public TextColor(EnumChatFormat legacyColor) {
            this.red = legacyColor.getRed();
            this.green = legacyColor.getGreen();
            this.blue = legacyColor.getBlue();
            this.hexCode = legacyColor.getHexCode();
        }

        /**
         * Constructs new instance with given parameters
         * @param red - red value
         * @param green - green value
         * @param blue - blue value
         */
        public TextColor(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        /**
         * Gets closest legacy color based on rgb values
         */
        private EnumChatFormat getClosestColor(int red, int green, int blue) {
            double minMaxDist = 9999;
            double maxDist;
            EnumChatFormat closestColor = EnumChatFormat.WHITE;
            for (EnumChatFormat color : legacyColors) {
                int rDiff = color.getRed() - red;
                int gDiff = color.getGreen() - green;
                int bDiff = color.getBlue() - blue;
                if (rDiff < 0) rDiff = -rDiff;
                if (gDiff < 0) gDiff = -gDiff;
                if (bDiff < 0) bDiff = -bDiff;
                maxDist = rDiff;
                if (gDiff > maxDist) maxDist = gDiff;
                if (bDiff > maxDist) maxDist = bDiff;
                if (maxDist < minMaxDist) {
                    minMaxDist = maxDist;
                    closestColor = color;
                }
            }
            return closestColor;
        }

        /**
         * Returns amount of red
         * @return amount of red
         */
        public int getRed() {
            if (red == null) {
                int hexColor = Integer.parseInt(hexCode.substring(1), 16);
                red = (hexColor >> 16) & 0xFF;
                green = (hexColor >> 8) & 0xFF;
                blue = hexColor & 0xFF;
            }
            return red;
        }

        /**
         * Returns amount of green
         * @return amount of green
         */
        public int getGreen() {
            if (green == null) {
                int hexColor = Integer.parseInt(hexCode.substring(1), 16);
                red = (hexColor >> 16) & 0xFF;
                green = (hexColor >> 8) & 0xFF;
                blue = hexColor & 0xFF;
            }
            return green;
        }

        /**
         * Returns amount of blue
         * @return amount of blue
         */
        public int getBlue() {
            if (blue == null) {
                int hexColor = Integer.parseInt(hexCode.substring(1), 16);
                red = (hexColor >> 16) & 0xFF;
                green = (hexColor >> 8) & 0xFF;
                blue = hexColor & 0xFF;
            }
            return blue;
        }

        /**
         * Returns defined legacy color
         * @return defined legacy color
         */
        public EnumChatFormat getLegacyColor() {
            if (legacyColor == null) {
                legacyColor = getClosestColor(getRed(), getGreen(), getBlue());
            }
            return legacyColor;
        }

        /**
         * Returns hex code of this color
         * @return hex code of this color
         */
        public String getHexCode() {
            if (hexCode == null) {
                hexCode = String.format("#%06X", (red << 16) + (green << 8) + blue);
            }
            return hexCode;
        }

        /**
         * Converts the color into a valid color value used in color field in chat component
         * @return the color converted into string acceptable by client
         */
        public String toString() {
            if (!returnLegacy) {
                EnumChatFormat legacyEquivalent = EnumChatFormat.fromRGBExact(getRed(), getGreen(), getBlue());
                if (legacyEquivalent != null) {
                    //not sending old colors as RGB to 1.16 clients if not needed as <1.16 servers will fail to apply color
                    return legacyEquivalent.toString().toLowerCase();
                }
                return getHexCode();
            } else {
                return getLegacyColor().toString().toLowerCase();
            }
        }

        /**
         * Returns true if legacy color was forced with a constructor, false if automatically
         * @return true if forced, false if not
         */
        public boolean isLegacyColorForced() {
            return legacyColorForced;
        }

        /**
         * Sets returnLegacy flag to given value
         * @param returnLegacy - true if color should return legacy
         */
        public void setReturnLegacy(boolean returnLegacy) {
            this.returnLegacy = returnLegacy;
        }

        /**
         * Reads the string and turns into text color. String is either #RRGGBB or a lowercased legacy color
         * @param string - string from color field in chat component
         * @return An instance from specified string
         */
        public static TextColor fromString(String string) {
            if (string == null) return null;
            if (string.startsWith("#")) return new TextColor(string);
            return new TextColor(EnumChatFormat.valueOf(string.toUpperCase()));
        }
    }
}
