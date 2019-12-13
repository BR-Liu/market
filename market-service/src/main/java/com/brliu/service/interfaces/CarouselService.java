package com.brliu.service.interfaces;

import com.brliu.domain.entity.Carousel;

import java.util.List;

public interface CarouselService {

    /**
     * 查询所有轮播图列表
     *
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);

}
