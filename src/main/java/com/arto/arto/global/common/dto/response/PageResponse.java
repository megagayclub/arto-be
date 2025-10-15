package com.arto.arto.global.common.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResponse<T> {
    private T data;
    private int page;
    private int size;
    private long total;


}
