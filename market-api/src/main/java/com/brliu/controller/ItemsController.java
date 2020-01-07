package com.brliu.controller;

import com.brliu.annotation.ResponseResult;
import com.brliu.domain.entity.Items;
import com.brliu.domain.entity.ItemsImg;
import com.brliu.domain.entity.ItemsParam;
import com.brliu.domain.entity.ItemsSpec;
import com.brliu.domain.vo.CommentLevelCountsVO;
import com.brliu.domain.vo.ItemInfoVO;
import com.brliu.domain.vo.ShopcartVO;
import com.brliu.service.interfaces.ItemService;
import com.brliu.utils.PagedGridResult;
import com.brliu.utils.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@ResponseResult
@RequestMapping("items")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemsController extends BaseController {

    private final ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public ResponseMessage info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return ResponseMessage.paramError("输入参数不能为空");
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return ResponseMessage.success(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public ResponseMessage commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return ResponseMessage.paramError("输入参数不能为空");
        }
        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);
        return ResponseMessage.success(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public ResponseMessage comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(itemId)) {
            return ResponseMessage.paramError("输入参数不能为空");
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);

        return ResponseMessage.success(grid);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public ResponseMessage search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(keywords)) {
            return ResponseMessage.paramError("输入参数不能为空");
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searhItems(keywords, sort, page, pageSize);

        return ResponseMessage.success(grid);
    }

    @ApiOperation(value = "通过分类id搜索商品列表", notes = "通过分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public ResponseMessage catItems(
            @ApiParam(name = "catId", value = "三级分类id", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (catId == null) {
            return ResponseMessage.paramError("输入参数错误");
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult grid = itemService.searhItems(catId,
                sort,
                page,
                pageSize);

        return ResponseMessage.success(grid);
    }

    // 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似京东淘宝
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public ResponseMessage refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1001,1003,1005")
            @RequestParam String itemSpecIds) {

        if (StringUtils.isBlank(itemSpecIds)) {
            return ResponseMessage.success();
        }

        List<ShopcartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);

        return ResponseMessage.success(list);
    }
}
