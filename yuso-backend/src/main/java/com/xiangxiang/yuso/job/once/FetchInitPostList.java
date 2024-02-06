package com.xiangxiang.yuso.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiangxiang.yuso.esdao.PostEsDao;
import com.xiangxiang.yuso.model.entity.Post;
import com.xiangxiang.yuso.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 全量同步帖子到 es
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;

    @Override
    public void run(String... args) {
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
        if (b){
            log.info("FetchInitPostList 成功，获取条数 = {}",postList.size());
        }else {
            log.info("失败");
        }
    }
}
