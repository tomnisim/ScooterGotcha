package gotcha.server.Utils;
import java.util.regex.*;


    public class AddressConverter {

        public static String convertToEnglish(String address) {
            String pattern = "\\p{InHebrew}+"; // Matches any Hebrew letter
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(address);
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                String hebrewAddress = matcher.group();
                String englishAddress = convertHebrewToEnglish(hebrewAddress);
                matcher.appendReplacement(buffer, englishAddress);
            }

            matcher.appendTail(buffer);
            return buffer.toString();
        }

        public static String convertHebrewToEnglish(String hebrewAddress) {
            // Add your conversion logic here
            // This example just replaces the Hebrew letters with their English counterparts
            String englishAddress = hebrewAddress
                    .replace("א", "A")
                    .replace("ב", "B")
                    .replace("ג", "G")
                    .replace("ד", "D")
                    .replace("ה", "HA")
                    .replace("ו", "V")
                    .replace("ז", "Z")
                    .replace("ח", "H")
                    .replace("ט", "T")
                    .replace("י", "I")
                    .replace("כ", "K")
                    .replace("ל", "L")
                    .replace("מ", "M")
                    .replace("ם", "M")
                    .replace("נ", "N")
                    .replace("ן", "N")
                    .replace("ס", "S")
                    .replace("ע", "P")
                    .replace("פ", "P")
                    .replace("צ", "TS")
                    .replace("ק", "K")
                    .replace("ר", "R")
                    .replace("ש", "SH")
                    .replace("ת", "T");

            return englishAddress;
        }
    }


