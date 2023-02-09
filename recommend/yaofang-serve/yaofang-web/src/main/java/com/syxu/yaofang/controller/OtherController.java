package com.syxu.yaofang.controller;

import com.syxu.yaofang.model.Doctor;
import com.syxu.yaofang.model.Result;
import com.syxu.yaofang.model.hosp;
import com.syxu.yaofang.model.keshi;
import com.syxu.yaofang.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OtherController {

    @Autowired
    private OtherService otherService;

    @PostMapping("/queryHosp/{city}")
    public Result queryHosp(@PathVariable("city") String city){
        List<hosp> hospList = otherService.findHospByCity(city);
        if(hospList!=null){
            return Result.success(hospList);
        }
        return Result.error();
    }

    @PostMapping("/queryKeshi/{hosp}")
    public Result queryKeshi(@PathVariable("hosp") String hosp){
        List<keshi> keshiList = otherService.findKeshiByHosp(hosp);
        if (keshiList!=null){
            return Result.success(keshiList);
        }
        return Result.error();
    }

    @PostMapping("/queryDoc/{keshi}/{hosp}/{city}")
    public Result queryDoc(@PathVariable("keshi") String keshi,@PathVariable("hosp") String hosp,@PathVariable("city") String city){
        List<Doctor> doctorList = otherService.findDocByKeShi(keshi,hosp,city);
        if(doctorList!=null){
            return Result.success(doctorList);
        }
        return Result.error();
    }
}
