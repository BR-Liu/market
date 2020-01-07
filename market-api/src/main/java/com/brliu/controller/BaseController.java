package com.brliu.controller;

import com.brliu.domain.entity.Orders;
import com.brliu.service.interfaces.MyOrdersService;
import com.brliu.utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class BaseController {

    static final String FOODIE_SHOPCART = "shopcart";

    static final Integer COMMON_PAGE_SIZE = 10;
    static final Integer PAGE_SIZE = 20;

    // 支付中心的调用地址
    String paymentUrl = "http://localhost:8071";		// produce

    // 微信支付成功 -> 支付中心 -> 平台端
    //                       |-> 回调通知的url
    String payReturnUrl = "http://localhost:8071/notifyMerchantOrderPaid";

    // 用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = File.separator + "workspaces" +
                                                            File.separator + "images" +
                                                            File.separator + "market" +
                                                            File.separator + "faces";


    @Autowired
    public MyOrdersService myOrdersService;

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public ResponseMessage checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return ResponseMessage.paramError("订单不存在！");
        }
        return ResponseMessage.success(order);
    }
}
