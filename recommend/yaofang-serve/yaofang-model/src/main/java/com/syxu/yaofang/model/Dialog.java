package com.syxu.yaofang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dialog {
    private Integer id;

    private Integer bingliId;

    private String zhusu;

    private String xbshi;

    private String jwshi;

    private String result;

    private String solution;

    private String yizhu;

    private Integer status;

    private Integer score;
}
