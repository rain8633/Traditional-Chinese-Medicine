package com.syxu.yaofang.service;

import com.syxu.yaofang.model.Doctor;
import com.syxu.yaofang.model.Page;

public interface DoctorService {

    int addDoctor(Doctor doctor);

    Doctor findDoctorByName(String dname);

    Doctor findDoctorByNameAndPw(String name, String password);

    Page<Doctor> queryDocList(Doctor doctor, Integer pageNum, Integer pageSize);

    Doctor queryDoctorById(Integer id);

    int updatedoctorMsg(Doctor doctor);

    int updatedoctorImg(Doctor doctor);

    Doctor queryDocByBingliId(Integer bingliId);
}
