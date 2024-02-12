package com.xiangxiang.yuso.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiangxiang.yuso.common.BaseResponse;
import com.xiangxiang.yuso.common.ErrorCode;
import com.xiangxiang.yuso.common.ResultUtils;
import com.xiangxiang.yuso.datasource.*;
import com.xiangxiang.yuso.exception.BusinessException;
import com.xiangxiang.yuso.exception.ThrowUtils;
import com.xiangxiang.yuso.model.dto.post.PostQueryRequest;
import com.xiangxiang.yuso.model.dto.search.SearchRequest;
import com.xiangxiang.yuso.model.dto.user.UserQueryRequest;
import com.xiangxiang.yuso.model.entity.Picture;
import com.xiangxiang.yuso.model.enums.SearchTypeEnum;
import com.xiangxiang.yuso.model.vo.PostVO;
import com.xiangxiang.yuso.model.vo.SearchVO;
import com.xiangxiang.yuso.model.vo.UserVO;
import com.xiangxiang.yuso.service.PictureService;
import com.xiangxiang.yuso.service.PostService;
import com.xiangxiang.yuso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面
 */
@Component
public class SearchFacade {


    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    @Resource
    private DataSourceRegister dataSourceRegister;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        if (searchTypeEnum == null) {
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText,current,pageSize);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText,current,pageSize);
                return postVOPage;
            });

            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, 1, 10);
                return picturePage;
            });

            CompletableFuture.allOf(userTask, postTask, pictureTask).join(); // 相当于断点
            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();

                SearchVO searchVO = new SearchVO();
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                return searchVO;
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        } else {
            SearchVO searchVO = new SearchVO();
            DataSource<?> dataSource = dataSourceRegister.getDataSourceByType(type);
            Page page = dataSource.doSearch(searchText, current, pageSize);
            searchVO.setDataList(page.getRecords());
            return searchVO;
        }
    }


}
