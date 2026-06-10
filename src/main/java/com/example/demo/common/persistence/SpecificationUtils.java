package com.example.demo.common.persistence;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;

public final class SpecificationUtils {

    private SpecificationUtils() {
    }

    public static <T> Specification<T> equalsIgnoreCase(String field, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank()) {
                return builder.conjunction();
            }
            return builder.equal(builder.lower(root.get(field)), value.toLowerCase(Locale.ROOT));
        };
    }

    public static <T> Specification<T> containsIgnoreCase(String field, String value) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get(field)), "%" + value.toLowerCase(Locale.ROOT) + "%");
        };
    }

    public static <T> Specification<T> multiFieldContains(String value, String... fields) {
        return (root, query, builder) -> {
            if (value == null || value.isBlank()) {
                return builder.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            for (String field : fields) {
                predicates.add(builder.like(builder.lower(root.get(field)), "%" + value.toLowerCase(Locale.ROOT) + "%"));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
