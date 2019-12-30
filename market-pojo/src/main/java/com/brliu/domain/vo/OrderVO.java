package com.brliu.domain.vo;


import com.brliu.domain.bo.ShoppingCartBO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
    private List<ShoppingCartBO> toBeRemovedShoppingCartList;

}