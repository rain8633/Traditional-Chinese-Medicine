package com.syxu.yaofang.pojo;

import com.syxu.yaofang.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorPoJo extends Doctor {

    private String cityName;

    private String hName;

    private String ksName;

}
