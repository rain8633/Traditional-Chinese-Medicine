package com.syxu.yaofang.mapper;

import com.syxu.yaofang.model.Doctor;
import com.syxu.yaofang.model.hosp;
import com.syxu.yaofang.model.keshi;

import java.util.List;

public interface OtherMapper {

    List<hosp> findHospByCity(String city);

    List<keshi> findKeshiByHosp(String hosp);

    List<Doctor> findDocByKeShi(String keshi,String hosp,String city);
}
