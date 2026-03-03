package br.com.patrimonium.common.util;

import br.com.patrimonium.common.dto.PageResponse;
import org.springframework.data.domain.Page;

public class PageMapper {

    public static <T> PageResponse<T> toResponse(Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}