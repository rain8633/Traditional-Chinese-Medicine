package com.syxu.yaofang.controller;

import com.syxu.yaofang.mapper.BingliMapper;
import com.syxu.yaofang.model.Bingli;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.model.Result;
import com.syxu.yaofang.pojo.BingLiPoJo;
import com.syxu.yaofang.service.BingliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bingli")
public class BingliController {

    @Autowired
    private BingliService bingliService;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
//        String extension = FilenameUtils.getExtension();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String prefix = UUID.randomUUID().toString().replaceAll("-", "");
        String newName=prefix+suffix;
        String path="H:/upload";

        file.transferTo(new File(path,newName));
        return Result.success("/pics/"+newName);
    }

    @PostMapping("/addBingLi")
    public Result AddBingLi(Bingli bingli){
        int i = bingliService.addBingLi(bingli);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

    @PostMapping("/queryAllBingli")
    public Result queryAllBingli(@RequestParam Integer doctorId,@RequestParam(required = false,defaultValue = "4") Integer pageSize, @RequestParam(required = false,defaultValue = "1") Integer pageNum){
        Page<BingLiPoJo> bingLiPoJoList = bingliService.queryAllBingli(doctorId,pageSize,pageNum);
        if(bingLiPoJoList!=null){
            return Result.success(bingLiPoJoList);
        }
        else return Result.error();
    }


    @PostMapping("/updateBingLiStatus/{bingliId}/{status}")
    public Result updateBingLiStatus(@PathVariable("bingliId") Integer bingliId,@PathVariable("status") Integer status){
        int i = bingliService.updateBingLiStatus(bingliId,status);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

    @PostMapping("/queryNewBingliNum/{doctorId}")
    public Result queryNewBingliNum(@PathVariable("doctorId") Integer doctorId){
        int num = bingliService.queryNewBingliNum(doctorId);
        return Result.success(num);
    }

    @GetMapping("/queryNewBingLi")
    public Result queryNewBingLi(@RequestParam Integer doctorId,@RequestParam(required = false,defaultValue = "4") Integer pageSize, @RequestParam(required = false,defaultValue = "1") Integer pageNum){
        Page<BingLiPoJo> bingLiPoJoList = bingliService.queryNewBingLi(doctorId,pageSize,pageNum);
        if(bingLiPoJoList!=null){
            return Result.success(bingLiPoJoList);
        }
        else return Result.error();
    }

    @PostMapping("/queryAllClientBingli")
    public Result queryAllClientBingli(@RequestParam Integer userId,@RequestParam(required = false,defaultValue = "4") Integer pageSize, @RequestParam(required = false,defaultValue = "1") Integer pageNum){
        Page<BingLiPoJo> bingLiPoJoList = bingliService.queryAllClientBingli(userId,pageSize,pageNum);
        if(bingLiPoJoList!=null){
            return Result.success(bingLiPoJoList);
        }
        else return Result.error();
    }

    @PostMapping("/queryBingLiById/{bingliId}")
    public Result queryBingLiById(@PathVariable("bingliId") Integer bingliId){
        BingLiPoJo bingLiPoJo =  bingliService.queryBingLiById(bingliId);
        return Result.success(bingLiPoJo);
    }
}
