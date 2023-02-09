package com.syxu.yaofang.mapper;

import com.syxu.yaofang.model.Doctor;

import java.util.List;

public interface DoctorMapper {
    int addDoctor(Doctor doctor);

    Doctor findDoctorByName(String dname);

    Doctor findDoctorByNameAndPw(String name, String password);

    List<Doctor> queryDocList(Doctor doctor);

    Doctor queryDoctorById(Integer id);

    int updatedoctorMsg(Doctor doctor);

    int updatedoctorImg(Doctor doctor);

    Doctor queryDocByBingliId(Integer bingliId);
}
