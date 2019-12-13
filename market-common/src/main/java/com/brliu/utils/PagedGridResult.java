package com.brliu.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description: 用来返回分页Grid的数据格式
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedGridResult {

    private int page;            // 当前页数
    private int total;            // 总页数
    private long records;        // 总记录数
    private List<?> rows;        // 每行显示的内容

}
