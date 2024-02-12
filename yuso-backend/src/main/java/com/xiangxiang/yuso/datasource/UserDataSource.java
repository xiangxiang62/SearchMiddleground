package com.xiangxiang.yuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiangxiang.yuso.model.dto.user.UserQueryRequest;
import com.xiangxiang.yuso.model.vo.UserVO;
import com.xiangxiang.yuso.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户服务实现
 */
@Service
@Slf4j
public class UserDataSource implements DataSource<UserVO>  {

    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        return userVOPage;
    }
}
