package com.xiangxiang.yuso.datasource;

import com.xiangxiang.yuso.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataSourceRegister {
    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    private Map<String, DataSource<T>> typeDataSourceMap;

    @PostConstruct
    public void doInit() {
        typeDataSourceMap = new HashMap() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        }};
    }


    public DataSource getDataSourceByType(String type) {
        if (typeDataSourceMap == null){
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
