package com.brliu.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "goods", type = "_doc", shards = 2) //shards属性与replica属性实际不生效
public class Goods {

    @Id
    private Long id;

    @Field(store = true, type = FieldType.Keyword)
    private String goodsName;

    @Field(store = true)
    private String goodsDescription;

    @Field(store = true, index = false)
    private String goodsCount;
}
