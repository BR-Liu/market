package com.brliu.domain.bo;

import lombok.*;

/**
 * 用于创建订单的BO对象
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderBO {

    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;

}
