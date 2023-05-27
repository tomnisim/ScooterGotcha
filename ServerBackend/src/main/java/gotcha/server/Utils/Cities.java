package gotcha.server.Utils;

public enum Cities {
    Beersheba,
    TelAviv,
    Netanya;

    public static String city_permutation(String city) {
        if (city.contains("באר") || city.contains("beer") || city.contains("sheba") || city.contains("Beer") || city.contains("Sheba")) {
            return Cities.Beersheba.name();
        }
        if (city.contains("אביב") || city.contains("tel") || city.contains("aviv") || city.contains("Tel") || city.contains("Aviv")) {
            return Cities.TelAviv.name();
        }
        return "default";
    }
}


