package com.brliu.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于展示商品搜索列表结果的VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchItemsVO {

    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;

}
