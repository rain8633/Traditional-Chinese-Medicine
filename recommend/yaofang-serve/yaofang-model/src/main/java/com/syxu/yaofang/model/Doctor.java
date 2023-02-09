package com.syxu.yaofang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    private Integer id;
    private String dname;
    private String password;
    private String phone;
    private String email;
    private Integer sex;
    private String cityName;
    private String hospName;
    private String ksName;
    private String pic;
    private String photo;
    private String info;
}
