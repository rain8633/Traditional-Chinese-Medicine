package com.syxu.yaofang.mapper;

import com.syxu.yaofang.model.Bingli;
import com.syxu.yaofang.pojo.BingLiPoJo;

import java.util.List;

public interface BingliMapper {

    int addBingLi(Bingli bingli);

    List<BingLiPoJo> queryAllBingli(Integer doctorId);

    int updateBingLiStatus(Integer bingliId, Integer status);

    int queryNewBingliNum(Integer doctorId);

    List<BingLiPoJo> queryNewBingLi(Integer doctorId);

    List<BingLiPoJo> queryAllClientBingli(Integer userId);

    BingLiPoJo queryBingLiById(Integer bingliId);
}
