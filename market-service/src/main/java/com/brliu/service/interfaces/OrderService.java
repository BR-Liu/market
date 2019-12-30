package com.brliu.service.interfaces;

import com.brliu.domain.bo.ShoppingCartBO;
import com.brliu.domain.bo.SubmitOrderBO;
import com.brliu.domain.entity.OrderStatus;
import com.brliu.domain.vo.OrderVO;

import java.util.List;

public interface OrderService {

    /**
     * 用于创建订单相关信息
     *
     * @param submitOrderBO
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO, List<ShoppingCartBO> shoppingCartList);

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     *
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
