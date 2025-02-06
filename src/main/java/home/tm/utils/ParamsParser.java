package home.tm.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ParamsParser {

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

    public static Sort parseSortQuery(String sortQuery) {
        if (sortQuery == null || sortQuery.trim().isEmpty()) {
            return Sort.unsorted(); // Vrátí prázdné třídění, pokud není zadáno
        }

        String[] sortParams = sortQuery.split(","); // Rozdělení podle čárky
        Sort sort = Sort.unsorted();

        for (String param : sortParams) {
            String[] parts = param.split(":");
            if (parts.length == 2) {
                String field = parts[0].trim();
                Sort.Direction direction = "DESC".equalsIgnoreCase(parts[1].trim())
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

                sort = sort.and(Sort.by(direction, field)); // Přidání dalšího řazení
            }
        }

        return sort;
    }

}
