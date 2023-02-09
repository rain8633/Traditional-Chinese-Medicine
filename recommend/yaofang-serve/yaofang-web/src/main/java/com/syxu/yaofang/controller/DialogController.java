package com.syxu.yaofang.controller;

import com.syxu.yaofang.model.Dialog;
import com.syxu.yaofang.model.Result;
import com.syxu.yaofang.pojo.DialogPoJo;
import com.syxu.yaofang.service.BingliService;
import com.syxu.yaofang.service.CommentService;
import com.syxu.yaofang.service.DialogService;
import info.debatty.java.stringsimilarity.JaroWinkler;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dialog")
public class DialogController {

    @Autowired
     private DialogService dialogService;

    @Autowired
    private BingliService bingliService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/addDialog")
    public Result addDialog(Dialog dialog){
        int i = dialogService.addDialog(dialog);
        if(i>0){
            bingliService.updateBingLiStatus(dialog.getBingliId(),2);
            return Result.success();
        }
        else return Result.error();
    }

    @PostMapping("/queryDialogByBingLiId/{bingliId}")
    public Result queryDialogByBingLiId(@PathVariable("bingliId") Integer bingliId){
        Dialog dialog = dialogService.queryDialogByBingLiId(bingliId);
        commentService.updateCommentStatus(bingliId,0);
        return Result.success(dialog);
    }

    @PostMapping("/queryDialogByBingLiIdNoStatus/{bingliId}")
    public Result queryDialogByBingLiIdNoStatus(@PathVariable("bingliId") Integer bingliId){
        Dialog dialog = dialogService.queryDialogByBingLiId(bingliId);
        return Result.success(dialog);
    }

    @PostMapping("/updateDialog")
    public Result updateDialog(Dialog dialog){
        int i = dialogService.updateDialog(dialog);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

    @PostMapping("/updateDialogScore")
    public Result updateDialogScore(@RequestParam Integer dialogId,@RequestParam Integer score){
        int i = dialogService.updateDialogScore(dialogId,score);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

    /**
     * 计算诊断结果之间的相似度，给出推荐列表
     * @param result
     * @return
     */
    @PostMapping("/queryDialogCommentList/{result}")
    public Result queryDialogCommentList(@PathVariable("result") String result){

        List<DialogPoJo> dialogPoJoList = dialogService.queryDialogRecommendList(result);
       if(dialogPoJoList!=null){
           return Result.success(dialogPoJoList);
       }
       return Result.error();
    }





}
