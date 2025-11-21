package com.arto.arto.global.common.dto.response;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResponse<T> {

    private List<T> data;       // ✅ 목록 데이터
    private int page;           // 현재 페이지 (1부터)
    private int size;           // 페이지당 개수
    private long totalElements; // 전체 데이터 개수
    private int totalPages;     // 전체 페이지 수

    // ✅ 정적 메서드 추가
    public static <T> PageResponse<T> of(Page<T> page) {
        PageResponse<T> response = new PageResponse<>();

        response.setData(page.getContent());
        response.setPage(page.getNumber() + 1); // JPA는 0부터, 응답은 1부터
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());

        return response;
    }
}
