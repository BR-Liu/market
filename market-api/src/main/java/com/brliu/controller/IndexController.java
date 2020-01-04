package com.brliu.controller;

import com.brliu.domain.entity.Carousel;
import com.brliu.domain.entity.Category;
import com.brliu.domain.vo.CategoryVO;
import com.brliu.domain.vo.NewItemsVO;
import com.brliu.enums.YesOrNoEnum;
import com.brliu.service.interfaces.CarouselService;
import com.brliu.service.interfaces.CategoryService;
import com.brliu.utils.JsonUtils;
import com.brliu.utils.RedisClient;
import com.brliu.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisClient redisClient;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public ResponseMessage carousel() {
        List<Carousel> list;
        String carouselStr = redisClient.get("carousel");
        if (StringUtils.isBlank(carouselStr)) {
            list = carouselService.queryAll(YesOrNoEnum.YES.type);
            redisClient.set("carousel", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }
        return ResponseMessage.success(list);
    }

    /**
     * 1. 后台运营系统，一旦广告（轮播图）发生更改，就可以删除缓存，然后重置
     * 2. 定时重置，比如每天凌晨三点重置
     * 3. 每个轮播图都有可能是一个广告，每个广告都会有一个过期时间，过期了，再重置
     */


    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public ResponseMessage cats() {
        List<Category> list = new ArrayList<>();
        String catsStr =  redisClient.get("cats");
        if (StringUtils.isBlank(catsStr)) {
            redisClient.set("cats", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(catsStr, Category.class);
        }
        return ResponseMessage.success(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public ResponseMessage subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return ResponseMessage.paramError("分类不存在");
        }

        List<CategoryVO> list;
        String catsStr = redisClient.get("subCat:" + rootCatId);
        if (StringUtils.isBlank(catsStr)) {
            list = categoryService.getSubCatList(rootCatId);
            /**
             * 查询的key在redis中不存在，
             * 对应的id在数据库也不存在，
             * 此时被非法用户进行攻击，大量的请求会直接打在db上，
             * 造成宕机，从而影响整个系统，
             * 这种现象称之为缓存穿透。
             * 解决方案：把空的数据也缓存起来，比如空字符串，空对象，空数组或list
             */
            if (list != null && list.size() > 0) {
                redisClient.set("subCat:" + rootCatId, JsonUtils.objectToJson(list));
            } else {
                redisClient.set("subCat:" + rootCatId, JsonUtils.objectToJson(list), 5 * 60, TimeUnit.SECONDS);
            }
        } else {
            list = JsonUtils.jsonToList(catsStr, CategoryVO.class);
        }

        return ResponseMessage.success(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public ResponseMessage sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return ResponseMessage.paramError("分类不存在");
        }

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return ResponseMessage.success(list);
    }

}
