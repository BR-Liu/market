package com.brliu.service.impl;

import com.brliu.domain.bo.OrderItemsCommentBO;
import com.brliu.domain.entity.OrderItems;
import com.brliu.domain.entity.OrderStatus;
import com.brliu.domain.entity.Orders;
import com.brliu.domain.vo.MyCommentVO;
import com.brliu.enums.YesOrNoEnum;
import com.brliu.mapper.ItemsCommentsMapperCustom;
import com.brliu.mapper.OrderItemsMapper;
import com.brliu.mapper.OrderStatusMapper;
import com.brliu.mapper.OrdersMapper;
import com.brliu.service.interfaces.MyCommentsService;
import com.brliu.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    public final OrderItemsMapper orderItemsMapper;

    public final OrdersMapper ordersMapper;

    public final OrderStatusMapper orderStatusMapper;

    public final ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    private final Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return orderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNoEnum.YES.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId,
                                           Integer page,
                                           Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        return setterPagedGrid(list, page);
    }
}
