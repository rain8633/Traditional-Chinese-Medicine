package com.syxu.yaofang.controller;


import com.syxu.yaofang.model.Comment;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.model.Result;
import com.syxu.yaofang.pojo.BingLiPoJo;
import com.syxu.yaofang.pojo.CommentPoJo;
import com.syxu.yaofang.service.CommentService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/addComment")
    public Result addComment(Comment comment){
        String date= DateFormatUtils.format(new Date(),"yyyy-MM-dd HH-mm-ss");
        comment.setCreateTime(date);
        int i=commentService.addComment(comment);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

    @PostMapping("/queryAllComment/{bingliId}")
    public Result queryAllComment(@PathVariable("bingliId") Integer bingliId){
        List<CommentPoJo> commentList = commentService.queryAllComment(bingliId);
        if(commentList!=null){
            return Result.success(commentList);
        }
        else return Result.error();
    }

    @PostMapping("/queryNewcommentNum/{id}")
    public Result queryNewcommentNum(@PathVariable("id") Integer id){
        int newCommentNum = commentService.queryNewcommentNum(id);
        return Result.success(newCommentNum);
    }

    @GetMapping("/queryNewComment")
    public Result queryNewComment(@RequestParam Integer doctorId,@RequestParam(required = false,defaultValue = "4") Integer pageSize, @RequestParam(required = false,defaultValue = "1") Integer pageNum){
        Page<BingLiPoJo> commentPoJoList = commentService.queryNewComment(doctorId,pageSize,pageNum);
        if(commentPoJoList!=null){
            return Result.success(commentPoJoList);
        }
        else return Result.error();

    }

    @PostMapping("/deleteCommentById/{id}")
    private Result deleteCommentById(@PathVariable("id") Integer id){
        int i = commentService.deleteCommentById(id);
        if(i>0){
            return Result.success();
        }
        else return Result.error();
    }

}
