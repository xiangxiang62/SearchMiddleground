package com.xiangxiang.yuso.model.vo;

import com.xiangxiang.yuso.model.entity.Picture;
import com.xiangxiang.yuso.model.entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class SearchVO {

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

}
