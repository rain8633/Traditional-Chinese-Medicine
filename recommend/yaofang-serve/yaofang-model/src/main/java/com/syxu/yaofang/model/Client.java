package com.syxu.yaofang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {
    private Integer id;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private Integer emailStatus;
    private String validateCode;
    private String img;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
