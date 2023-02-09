package com.syxu.yaofang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T>  {
    private int pageNum;
    //每页记录数
    private int pageSize;
    //列表数据
    private List<T> rows;
    //总页数
    private int pageCount;
    //总记录数
    private long total;

}
