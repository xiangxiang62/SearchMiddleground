package com.xiangxiang.yuso.model.dto.picture;

import com.xiangxiang.yuso.common.PageRequest;
import lombok.Data;


@Data
public class PictureQueryRequest extends PageRequest {

    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;
}
