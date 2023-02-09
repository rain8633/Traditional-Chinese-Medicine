package com.syxu.yaofang.service.Impl;

import com.syxu.yaofang.mapper.DialogMapper;
import com.syxu.yaofang.model.Dialog;
import com.syxu.yaofang.pojo.DialogPoJo;
import com.syxu.yaofang.service.DialogService;
import info.debatty.java.stringsimilarity.JaroWinkler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DialogServiceImpl implements DialogService {

    @Autowired
    private DialogMapper dialogMapper;

    @Override
    public int addDialog(Dialog dialog) {
        return dialogMapper.addDialog(dialog);
    }

    @Override
    public Dialog queryDialogByBingLiId(Integer bingliId) {
        return dialogMapper.queryDialogByBingLiId(bingliId);
    }

    @Override
    public int updateDialog(Dialog dialog) {

        return dialogMapper.updateDialog(dialog);
    }

    @Override
    public int updateDialogScore(Integer dialogId, Integer score) {
        return dialogMapper.updateDialogScore(dialogId,score);
    }

    @Override
    public List<DialogPoJo> queryDialogRecommendList(String result) {

        List<DialogPoJo> dialogPoJoList = dialogMapper.queryDialogRecommendList();
        JaroWinkler jaroWinkler = new JaroWinkler();//创建JaroWinkler对象
        List<DialogPoJo> dialogPoJos = new ArrayList<>();
        for(DialogPoJo dialogPoJo : dialogPoJoList){

            Double similar = jaroWinkler.similarity(dialogPoJo.getResult(),result);//计算相似度
            dialogPoJo.setSimilar(similar);//将相似度添加给对应的病历
            dialogPoJos.add(dialogPoJo);
        }

        Collections.sort(dialogPoJos, new Comparator<DialogPoJo>() {
            @Override
            public int compare(DialogPoJo o1, DialogPoJo o2) {
                return o1.getSimilar()>o2.getSimilar()?-1:o1.getSimilar().equals(o2.getSimilar())?0:1;
            }//将各个病历按相似度排序，给出相似度较高的病历
        });

        return dialogPoJos;
    }

}
