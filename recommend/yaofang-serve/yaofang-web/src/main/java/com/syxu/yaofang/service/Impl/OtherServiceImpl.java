package com.syxu.yaofang.service.Impl;

import com.syxu.yaofang.mapper.OtherMapper;
import com.syxu.yaofang.model.Doctor;
import com.syxu.yaofang.model.hosp;
import com.syxu.yaofang.model.keshi;
import com.syxu.yaofang.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OtherServiceImpl implements OtherService {

    @Autowired
    private OtherMapper otherMapper;

    @Override
    public List<hosp> findHospByCity(String city) {
        return otherMapper.findHospByCity(city);
    }

    @Override
    public List<keshi> findKeshiByHosp(String hosp) {
        return otherMapper.findKeshiByHosp(hosp);
    }

    @Override
    public List<Doctor> findDocByKeShi(String keshi,String hosp,String city) {
        return otherMapper.findDocByKeShi(keshi,hosp,city);
    }
}
