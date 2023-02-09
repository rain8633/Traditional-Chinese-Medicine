package com.syxu.yaofang.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bingli {

    private Integer id;

    private Integer clientId;

    private Integer doctorId;

    private String realName;

    private Integer clientAge;

    private String info;

    private String pic1;

    private String pic2;

    private String pic3;

    private Integer status;//0 未查看 1已查看 2已诊断 3已解决
}
