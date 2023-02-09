package com.syxu.yaofang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    private Integer id;

    private Integer bingliId;

    private String content;

    private String createTime;

    private Integer status;

    private Integer role;




}
