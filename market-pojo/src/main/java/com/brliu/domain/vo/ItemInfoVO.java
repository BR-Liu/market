package com.brliu.domain.vo;

import com.brliu.domain.entity.Items;
import com.brliu.domain.entity.ItemsImg;
import com.brliu.domain.entity.ItemsParam;
import com.brliu.domain.entity.ItemsSpec;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品详情VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

}
