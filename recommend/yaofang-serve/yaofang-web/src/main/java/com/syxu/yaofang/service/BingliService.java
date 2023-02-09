package com.syxu.yaofang.service;

import com.syxu.yaofang.model.Bingli;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.pojo.BingLiPoJo;

import java.util.List;

public interface BingliService {

   int addBingLi(Bingli bingli);

   Page<BingLiPoJo> queryAllBingli(Integer doctorId,Integer pageSize,Integer pageNum);

   int updateBingLiStatus(Integer bingliId, Integer status);

    int queryNewBingliNum(Integer doctorId);

    Page<BingLiPoJo> queryNewBingLi(Integer doctorId,Integer pageSize,Integer pageNum);

    Page<BingLiPoJo> queryAllClientBingli(Integer userId, Integer pageSize, Integer pageNum);

    BingLiPoJo queryBingLiById(Integer bingliId);
}
