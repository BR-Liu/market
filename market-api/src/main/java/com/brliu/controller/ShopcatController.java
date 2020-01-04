package com.brliu.controller;

import com.brliu.annotation.ResponseResult;
import com.brliu.domain.bo.ShoppingCartBO;
import com.brliu.enums.ResponseStateEnum;
import com.brliu.exception.RestMessageException;
import com.brliu.utils.JsonUtils;
import com.brliu.utils.RedisClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "购物车接口controller", tags = {"购物车接口相关的api"})
@RequestMapping("shopcart")
@ResponseResult
@RestController
public class ShopcatController extends BaseController {

    @Autowired
    private RedisClient redisClient;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public void add(@RequestParam String userId, @RequestBody ShoppingCartBO shoppingCartBO) {

        if (StringUtils.isBlank(userId)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "参数不能为空");
        }

        System.out.println(shoppingCartBO);

        // 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        // 需要判断当前购物车中包含已经存在的商品，如果存在则累加购买数量
        String shoppingCartJson = redisClient.get(FOODIE_SHOPCART + ":" + userId);
        List<ShoppingCartBO> shoppingCartList;
        if (StringUtils.isNotBlank(shoppingCartJson)) {
            // redis中已经有购物车了
            shoppingCartList = JsonUtils.jsonToList(shoppingCartJson, ShoppingCartBO.class);
            // 判断购物车中是否存在已有商品，如果有的话counts累加
            boolean isHaving = false;
            for (ShoppingCartBO sc : shoppingCartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(shoppingCartBO.getSpecId())) {
                    sc.setBuyCounts(sc.getBuyCounts() + shoppingCartBO.getBuyCounts());
                    isHaving = true;
                }
            }
            if (!isHaving) {
                shoppingCartList.add(shoppingCartBO);
            }
        } else {
            // redis中没有购物车
            shoppingCartList = new ArrayList<>();
            // 直接添加到购物车中
            shoppingCartList.add(shoppingCartBO);
        }

        // 覆盖现有redis中的购物车
        redisClient.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shoppingCartList));

    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public void del(@RequestParam String userId, @RequestParam String itemSpecId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            throw new RestMessageException(ResponseStateEnum.PARAM_ERROR, "参数不能为空");
        }

        // 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除redis购物车中的商品
        String shoppingCartJson = redisClient.get(FOODIE_SHOPCART + ":" + userId);
        if (StringUtils.isNotBlank(shoppingCartJson)) {
            // redis中已经有购物车了
            List<ShoppingCartBO> shoppingCartList = JsonUtils.jsonToList(shoppingCartJson, ShoppingCartBO.class);
            // 判断购物车中是否存在已有商品，如果有的话则删除
            for (ShoppingCartBO sc : shoppingCartList) {
                String tmpSpecId = sc.getSpecId();
                if (tmpSpecId.equals(itemSpecId)) {
                    shoppingCartList.remove(sc);
                    break;
                }
            }
            // 覆盖现有redis中的购物车
            redisClient.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shoppingCartList));
        }
    }

}
