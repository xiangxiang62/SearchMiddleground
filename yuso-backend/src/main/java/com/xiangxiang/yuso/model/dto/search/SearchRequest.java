package com.xiangxiang.yuso.model.dto.search;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 类型
     */
    private String type;

    private static final long serialVersionUID = 1L;
}
