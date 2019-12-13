package com.brliu.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

}