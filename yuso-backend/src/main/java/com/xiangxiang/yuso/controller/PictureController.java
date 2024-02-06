package com.xiangxiang.yuso.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiangxiang.yuso.annotation.AuthCheck;
import com.xiangxiang.yuso.common.BaseResponse;
import com.xiangxiang.yuso.common.DeleteRequest;
import com.xiangxiang.yuso.common.ErrorCode;
import com.xiangxiang.yuso.common.ResultUtils;
import com.xiangxiang.yuso.constant.UserConstant;
import com.xiangxiang.yuso.exception.BusinessException;
import com.xiangxiang.yuso.exception.ThrowUtils;
import com.xiangxiang.yuso.model.dto.picture.PictureQueryRequest;
import com.xiangxiang.yuso.model.dto.post.PostAddRequest;
import com.xiangxiang.yuso.model.dto.post.PostEditRequest;
import com.xiangxiang.yuso.model.dto.post.PostQueryRequest;
import com.xiangxiang.yuso.model.dto.post.PostUpdateRequest;
import com.xiangxiang.yuso.model.entity.Picture;
import com.xiangxiang.yuso.model.entity.Post;
import com.xiangxiang.yuso.model.entity.User;
import com.xiangxiang.yuso.model.vo.PostVO;
import com.xiangxiang.yuso.service.PictureService;
import com.xiangxiang.yuso.service.PostService;
import com.xiangxiang.yuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
@Service
public class PictureController {

    @Resource
    private PictureService pictureService;

    /**
     * 分页获取列表（封装类）
     *
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPostVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                        HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> picturePage = pictureService.searchPicture(searchText, current, size);
        return ResultUtils.success(picturePage);
    }

}
