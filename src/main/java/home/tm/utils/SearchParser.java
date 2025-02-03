package home.tm.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class SearchParser {

    public static Map<String, String> parseSearchQuery(String search) {
        Map<String, String> filters = new HashMap<>();

        if (search != null && !search.isEmpty()) {
            String[] parts = search.split(" AND ");
            for (String part : parts) {
                String[] keyValue = part.split("=");
                if (keyValue.length == 2) {
                    filters.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        }
        return filters;
    }
}
