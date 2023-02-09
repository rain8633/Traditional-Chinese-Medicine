package com.syxu.yaofang.pojo;

import com.syxu.yaofang.model.Dialog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialogPoJo extends Dialog {

    private String dname;

    private String ksName;

    private Double similar;

}
