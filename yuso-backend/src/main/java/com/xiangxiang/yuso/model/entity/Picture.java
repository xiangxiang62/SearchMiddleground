package com.xiangxiang.yuso.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Picture implements Serializable {

    private String title;

    private String url;
}
