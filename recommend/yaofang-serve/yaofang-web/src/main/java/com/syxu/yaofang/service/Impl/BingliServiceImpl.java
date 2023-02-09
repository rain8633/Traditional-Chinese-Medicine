package com.syxu.yaofang.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syxu.yaofang.mapper.BingliMapper;
import com.syxu.yaofang.model.Bingli;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.pojo.BingLiPoJo;
import com.syxu.yaofang.service.BingliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BingliServiceImpl implements BingliService {

    @Autowired
    private BingliMapper bingliMapper;


    @Override
    public int addBingLi(Bingli bingli) {
        return bingliMapper.addBingLi(bingli);
    }

    @Override
    public Page<BingLiPoJo> queryAllBingli(Integer doctorId,Integer pageSize,Integer pageNum)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<BingLiPoJo> bingLiPoJoList=bingliMapper.queryAllBingli(doctorId);
        PageInfo<BingLiPoJo> pageInfo=new PageInfo<>(bingLiPoJoList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                pageInfo.getList(),pageInfo.getPages(),pageInfo.getTotal());
    }

    @Override
    public int updateBingLiStatus(Integer bingliId, Integer status) {
        return bingliMapper.updateBingLiStatus(bingliId,status);
    }

    @Override
    public int queryNewBingliNum(Integer doctorId) {
        return bingliMapper.queryNewBingliNum(doctorId);
    }

    @Override
    public Page<BingLiPoJo> queryNewBingLi(Integer doctorId,Integer pageSize,Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);
        List<BingLiPoJo> bingLiPoJoList=bingliMapper.queryNewBingLi(doctorId);
        PageInfo<BingLiPoJo> pageInfo=new PageInfo<>(bingLiPoJoList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                pageInfo.getList(),pageInfo.getPages(),pageInfo.getTotal());
    }

    @Override
    public Page<BingLiPoJo> queryAllClientBingli(Integer userId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<BingLiPoJo> bingLiPoJoList=bingliMapper.queryAllClientBingli(userId);
        PageInfo<BingLiPoJo> pageInfo=new PageInfo<>(bingLiPoJoList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                pageInfo.getList(),pageInfo.getPages(),pageInfo.getTotal());
    }

    @Override
    public BingLiPoJo queryBingLiById(Integer bingliId) {
        return bingliMapper.queryBingLiById(bingliId);
    }
}
