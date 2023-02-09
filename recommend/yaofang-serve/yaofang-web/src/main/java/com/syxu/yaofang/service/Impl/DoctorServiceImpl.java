package com.syxu.yaofang.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syxu.yaofang.mapper.DoctorMapper;
import com.syxu.yaofang.model.Doctor;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.pojo.BingLiPoJo;
import com.syxu.yaofang.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public int addDoctor(Doctor doctor) {
        return doctorMapper.addDoctor(doctor);
    }

    @Override
    public Doctor findDoctorByName(String dname) {
        return doctorMapper.findDoctorByName(dname);
    }

    @Override
    public Doctor findDoctorByNameAndPw(String name, String password) {
        return doctorMapper.findDoctorByNameAndPw(name,password);
    }

    @Override
    public Page<Doctor> queryDocList(Doctor doctor, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Doctor> DoctorList=doctorMapper.queryDocList(doctor);
        PageInfo<Doctor> pageInfo=new PageInfo<>(DoctorList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                pageInfo.getList(),pageInfo.getPages(),pageInfo.getTotal());
    }

    @Override
    public Doctor queryDoctorById(Integer id) {
        return doctorMapper.queryDoctorById(id);
    }

    @Override
    public int updatedoctorMsg(Doctor doctor) {
        return doctorMapper.updatedoctorMsg(doctor);
    }

    @Override
    public int updatedoctorImg(Doctor doctor) {
        return doctorMapper.updatedoctorImg(doctor);
    }

    @Override
    public Doctor queryDocByBingliId(Integer bingliId) {
        return doctorMapper.queryDocByBingliId(bingliId);
    }
}
