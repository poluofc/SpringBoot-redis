package com.somta.springboot;

/**
 * @ClassName UserTest
 * @Description TODO
 * @Author tank
 * @dATE 2021/9/10 17:21
 * VERSION 1.0
 **/
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;;
import com.somta.springboot.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Resource
    private ElasticsearchRestTemplate template;

    @Resource
    private RestHighLevelClient client;

    @Test
    public void testCreateIndexAndDoc() throws IOException {

        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("user");
        //2.执行客户端请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        log.info("创建索引{}成功", response);
        System.out.println("response.isAcknowledged() = " + response.isAcknowledged());


    }

    @Test
    public void saveDoc() throws IOException {
        String index = "1";

        User user = new User();
        user.setId("12345");
        user.setAge(20);
        user.setName("狗蛋");

        //创建请求
        IndexRequest request = new IndexRequest("user");

        //规则 put /test_index/_doc/1
        request.id(index);
        request.timeout(TimeValue.timeValueSeconds(1));
        //将数据放入请求 json
        request.source(user, XContentType.JSON);
        //客户端发送请求
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        log.info("添加数据成功 索引为: {}, response 状态: {}, id为: {}",index,response.status().getStatus(), response.getId());
        log.info("=========={}",response.getId());

    }

    @Test
    public void saveDoc2() throws IOException {
        IndexRequest request = new IndexRequest("user");

        User user = new User();
        user.setId("12345");
        user.setAge(20);
        user.setName("狗蛋");

//        String s = JSONUtil.pa

        request.id("1");
        String jsonString = "{" +
                "\"id\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";


//        request.source(jsonString, XContentType.JSON);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(user);
        request.source(stringObjectMap);

//        request.source()

        //客户端发送请求
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        log.info("添加数据成功 索引为: {}, response 状态: {}, id为: {}","user",response.status().getStatus(), response.getId());
        log.info("=========={}",response.getId());


    }


    @Test
    public void testStrToBean() {
        User user = new User();
        user.setId("12345");
        user.setAge(20);
        user.setName("狗蛋");

        JSONObject jsonObject = JSONUtil.parseObj(user);

        System.out.println(jsonObject.toString());
    }

    @Test
    public void saveDocRandomId() throws IOException {
        IndexRequest request = new IndexRequest("user");

        User user = new User();
        user.setId("2343321");
        user.setAge(30);
        user.setName("狗蛋333");

//        request.id("1");

        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(user);
        request.source(stringObjectMap);

//        request.source()

        //客户端发送请求
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
//        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

        log.info("添加数据成功 索引为: {}, response 状态: {}, id为: {}","user",response.status().getStatus(), response.getId());
        log.info("=========={}",response.getId());

    }
}
