package com.brliu.controller;


import com.alibaba.fastjson.JSONObject;
import com.brliu.annotation.ResponseResult;
import com.brliu.domain.bo.ShoppingCartBO;
import com.brliu.domain.bo.SubmitOrderBO;
import com.brliu.domain.entity.OrderStatus;
import com.brliu.domain.vo.MerchantOrdersVO;
import com.brliu.domain.vo.OrderVO;
import com.brliu.enums.OrderStatusEnum;
import com.brliu.enums.PayMethodEnum;
import com.brliu.enums.ResponseStateEnum;
import com.brliu.exception.RestMessageException;
import com.brliu.service.interfaces.OrderService;
import com.brliu.utils.CookieUtils;
import com.brliu.utils.RedisClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@ResponseResult
@RequestMapping("order")
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrdersController {

    private static final String SHOPPING_CART = "shoppingCart";

    private final OrderService orderService;

    private final RedisClient redisClient;

    @Value("${pay.center.host}")
    private String paymentCenterHost;


    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/createOrder")
    public void create(@RequestBody SubmitOrderBO submitOrderBO,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        if (submitOrderBO.getPayMethod().equals(PayMethodEnum.WEIXIN.type)
                && submitOrderBO.getPayMethod().equals(PayMethodEnum.ALIPAY.type)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "支付方式不支持");
        }

        String shoppingCartJson = redisClient.get(SHOPPING_CART + ":" + submitOrderBO.getUserId());
        if (StringUtils.isBlank(shoppingCartJson)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "购物车数据不正确");
        }

        List<ShoppingCartBO> shoppingCartList = JSONObject.parseArray(shoppingCartJson, ShoppingCartBO.class);

        // 1. 创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO, shoppingCartList);
        String orderId = orderVO.getOrderId();

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        // 清理覆盖现有的redis汇总的购物数据
        shoppingCartList.removeAll(orderVO.getToBeRemovedShoppingCartList());
        redisClient.set(SHOPPING_CART + ":" + submitOrderBO.getUserId(),
                JSONObject.toJSONString(shoppingCartList));
        // 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request,
                response,
                SHOPPING_CART,
                JSONObject.toJSONString(shoppingCartList), true);

        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(paymentCenterHost);

        // 为了方便测试购买，所以所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);
        // TODO: 2019/12/30 后续补充涉及支付中心的内容
    }

    @PostMapping("/notifyPaidOrder")
    public void notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
    }

    @PostMapping("/getPaidOrderInfo")
    public OrderStatus getPaidOrderInfo(String orderId) {
        return orderService.queryOrderStatusInfo(orderId);
    }
}
