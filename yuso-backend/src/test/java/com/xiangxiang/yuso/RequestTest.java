package com.xiangxiang.yuso;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiangxiang.yuso.model.entity.Picture;
import com.xiangxiang.yuso.model.entity.Post;
import com.xiangxiang.yuso.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
@SpringBootTest
@Slf4j
public class RequestTest {

    @Resource
    private PostService postService;


    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://www.bing.com/images/search?q=鸡哥&form=HDRSC2&cw=1263&ch=601&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            // 取图片地址（murl）
            String m = element.select(".iusc").get(0).attr("m");
            // json 转对象
            Map<String,Object> map = JSONUtil.toBean(m,Map.class);
            // 标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            String murl = (String)map.get("murl");
            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
        }
    }



    @Test
    void testPost() {
        // 获取数据
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
        System.out.println(result);
        // json 转对象
        Map<String,Object> map = JSONUtil.toBean(result,Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray)data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));

            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));

            post.setUserId(1L);
            postList.add(post);
        }
//        System.out.println(postList);
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }


}
