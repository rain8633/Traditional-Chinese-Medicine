package com.syxu.yaofang.service;

import com.syxu.yaofang.model.Comment;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.pojo.BingLiPoJo;
import com.syxu.yaofang.pojo.CommentPoJo;

import java.util.List;

public interface CommentService {

    int addComment(Comment comment);

    List<CommentPoJo> queryAllComment(Integer bingliId);

    int queryNewcommentNum(Integer id);

    Page<BingLiPoJo> queryNewComment(Integer doctorId, Integer pageSize, Integer pageNum);

    int updateCommentStatus(Integer bingliId,Integer role);

    int deleteCommentById(Integer id);
}
