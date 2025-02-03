package home.tm.utils;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class SearchParser {

    public Map<String, String> parseSearchQuery(String search) {
        Map<String, String> filters = new HashMap<>();

        if (search != null && !search.isEmpty()) {
            Pattern pattern = Pattern.compile("(\\w+):([\\w*.-]+)");
            Matcher matcher = pattern.matcher(search);

            while (matcher.find()) {
                filters.put(matcher.group(1), matcher.group(2));
            }
        }
        return filters;
    }
}
