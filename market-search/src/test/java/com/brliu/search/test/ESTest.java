package com.brliu.search.test;

import com.brliu.search.pojo.Goods;
import org.elasticsearch.action.index.IndexRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESTest {

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Test
    public void createIndex() {
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withObject(Goods.builder()
                        .id(1000L)
                        .goodsName("测试商品")
                        .goodsDescription("这是一个测试商品")
                        .goodsCount("2")
                        .build()
                )
                .build();
        esTemplate.index(indexQuery);
    }


    @Test
    public void deleteIndex() {
        esTemplate.deleteIndex(Goods.class);
    }

    @Test
    public void updateIndex() {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsCount", "3");
        UpdateQuery updateQuery = new UpdateQueryBuilder()
                .withClass(Goods.class)
                .withId("1000")
                .withIndexRequest(new IndexRequest().source(map))
                .build();
        esTemplate.update(updateQuery);
    }
}
