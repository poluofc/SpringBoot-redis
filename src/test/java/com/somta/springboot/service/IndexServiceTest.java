package com.somta.springboot.service;

import com.alibaba.fastjson.JSON;
import com.somta.springboot.pojo.UserInfo;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexServiceTest extends TestCase {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testAddDocument() {
        try {
            // 创建索引请求对象
            IndexRequest indexRequest = new IndexRequest("mydlq-user", "doc", "1");
            // 创建员工信息
            UserInfo userInfo = new UserInfo();
            userInfo.setName("张三");
            userInfo.setAge(29);
            userInfo.setSalary(100.00f);
            userInfo.setAddress("北京市");
            userInfo.setRemark("来自北京市的张先生");
            userInfo.setCreateTime(new Date());
            userInfo.setBirthDate("1990-01-10");
            // 将对象转换为 byte 数组
            byte[] json = JSON.toJSONBytes(userInfo);
            // 设置文档内容
            indexRequest.source(json, XContentType.JSON);
            // 执行增加文档
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("创建状态：{}", response.status());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void testGetDocument() {
    }

    public void testUpdateDocument() {
    }

    public void testDeleteDocument() {
    }
}
