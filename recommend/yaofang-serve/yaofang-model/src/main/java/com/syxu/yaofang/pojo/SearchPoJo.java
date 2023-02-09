package com.syxu.yaofang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.logging.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPoJo {
    private Integer id;

    private String name;

    private String xingzhuang;

    private String wei;

    private String yongliang;

    private String gongxiao;

    private String chuchu;

    private String chufang;

    private String zhuzhi;

    private String src;

    private Integer type;//type为0是药材 type为1 是药方
}
