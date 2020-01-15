package com.brliu.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "goods", type = "_doc", shards = 2)
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
