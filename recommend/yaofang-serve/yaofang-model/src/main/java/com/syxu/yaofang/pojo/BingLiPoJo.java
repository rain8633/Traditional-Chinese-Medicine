package com.syxu.yaofang.pojo;

import com.syxu.yaofang.model.Bingli;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BingLiPoJo extends Bingli {

    private String userName;

    private String email;

    private String phone;

    private String dname;

    private String ksName;

    private String content;

}
