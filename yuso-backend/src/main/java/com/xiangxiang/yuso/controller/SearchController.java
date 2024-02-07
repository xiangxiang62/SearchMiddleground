package com.xiangxiang.yuso.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiangxiang.yuso.common.BaseResponse;
import com.xiangxiang.yuso.common.ErrorCode;
import com.xiangxiang.yuso.common.ResultUtils;
import com.xiangxiang.yuso.exception.ThrowUtils;
import com.xiangxiang.yuso.model.dto.picture.PictureQueryRequest;
import com.xiangxiang.yuso.model.dto.post.PostQueryRequest;
import com.xiangxiang.yuso.model.dto.search.SearchRequest;
import com.xiangxiang.yuso.model.dto.user.UserQueryRequest;
import com.xiangxiang.yuso.model.entity.Picture;
import com.xiangxiang.yuso.model.vo.PostVO;
import com.xiangxiang.yuso.model.vo.SearchVO;
import com.xiangxiang.yuso.model.vo.UserVO;
import com.xiangxiang.yuso.service.PictureService;
import com.xiangxiang.yuso.service.PostService;
import com.xiangxiang.yuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
@Service
public class SearchController {

    @Resource
    private PictureService pictureService;

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;


    /**
     * 分页获取列表（封装类）
     *
     * @param searchRequest
     * @return
     */
    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest,HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);

        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);

        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);

        SearchVO searchVO = new SearchVO();
        searchVO.setUserList(userVOPage.getRecords());
        searchVO.setPostList(postVOPage.getRecords());
        searchVO.setPictureList(picturePage.getRecords());

        return ResultUtils.success(searchVO);
    }

}
