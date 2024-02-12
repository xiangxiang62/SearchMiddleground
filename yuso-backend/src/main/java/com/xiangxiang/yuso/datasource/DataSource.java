package com.xiangxiang.yuso.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.formula.functions.T;

/**
 * 数据杰阔校验（新接入的数据源必须实现）
 */
public interface DataSource<T> {
    /**
     * 搜索
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
