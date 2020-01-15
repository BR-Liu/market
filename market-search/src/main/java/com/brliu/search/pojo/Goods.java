package com.brliu.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "Goods", type = "_doc")
public class Goods {

    @Id
    private Long id;

    @Field(store = true)
    private String goodsName;

    @Field(store = true)
    private String goodsDescription;

    @Field(store = true, index = false)
    private String goodsCount;
}
