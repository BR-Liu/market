package com.brliu.mapper;

import com.brliu.domain.entity.ItemsComments;
import com.brliu.domain.vo.MyCommentVO;
import com.brliu.mymapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    void saveComments(Map<String, Object> map);

    List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}