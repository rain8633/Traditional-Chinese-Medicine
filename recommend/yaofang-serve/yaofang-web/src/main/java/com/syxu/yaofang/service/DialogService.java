package com.syxu.yaofang.service;

import com.syxu.yaofang.model.Dialog;
import com.syxu.yaofang.pojo.DialogPoJo;

import java.util.List;

public interface DialogService {

    int addDialog(Dialog dialog);

    Dialog queryDialogByBingLiId(Integer bingliId);

    int updateDialog(Dialog dialog);

    int updateDialogScore(Integer dialogId, Integer score);

    List<DialogPoJo> queryDialogRecommendList(String result);
}
