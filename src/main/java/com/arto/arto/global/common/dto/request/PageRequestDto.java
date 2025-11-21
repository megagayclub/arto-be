package com.arto.arto.global.common.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
public class PageRequestDto {
    private int page;
    private int size;

    public PageRequestDto() {
        this.page = 1;      // 1페이지부터 시작 (사용자 관점)
        this.size = 10;
    }
    public Pageable toPageable() {
        int pageIndex = Math.max(this.page - 1, 0); // JPA는 0부터 시작
        int pageSize = this.size <= 0 ? 10 : this.size;

        return PageRequest.of(pageIndex, pageSize);
    }
}
