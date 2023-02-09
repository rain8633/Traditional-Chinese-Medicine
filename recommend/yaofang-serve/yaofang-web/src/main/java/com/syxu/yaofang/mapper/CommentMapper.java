package com.syxu.yaofang.mapper;

import com.syxu.yaofang.model.Comment;
import com.syxu.yaofang.pojo.BingLiPoJo;
import com.syxu.yaofang.pojo.CommentPoJo;

import java.util.List;

public interface CommentMapper {

    int addComment(Comment comment);

    List<CommentPoJo> queryAllComment(Integer bingliId);

    int queryNewcommentNum(Integer id);

    List<BingLiPoJo> queryNewComment(Integer doctorId);

    int updateCommentStatus(Integer bingliId,Integer role);

    int deleteCommentById(Integer id);
}
