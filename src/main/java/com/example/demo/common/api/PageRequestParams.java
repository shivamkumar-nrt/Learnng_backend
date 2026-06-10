package com.example.demo.common.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record PageRequestParams(
        @Min(0) Integer page,
        @Min(1) @Max(100) Integer size,
        String sortBy,
        String sortDir
) {

    public int safePage() {
        return page == null ? 0 : page;
    }

    public int safeSize() {
        return size == null ? 10 : size;
    }

    public String safeSortDir() {
        return sortDir == null || sortDir.isBlank() ? "desc" : sortDir;
    }
}
