package com.syxu.yaofang.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syxu.yaofang.mapper.CommentMapper;
import com.syxu.yaofang.model.Comment;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.pojo.BingLiPoJo;
import com.syxu.yaofang.pojo.CommentPoJo;
import com.syxu.yaofang.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public int addComment(Comment comment) {
        redisTemplate.delete("commentList");
        return commentMapper.addComment(comment);
    }

    @Override
    public List<CommentPoJo> queryAllComment(Integer bingliId) {

        List<CommentPoJo> commentPoJoList = (List<CommentPoJo>) redisTemplate.opsForValue().get("commentList");
        if(commentPoJoList==null){
            commentPoJoList=commentMapper.queryAllComment(bingliId);
            redisTemplate.opsForValue().set("commentList",commentPoJoList);
            return commentPoJoList;
        }
        return commentPoJoList;
//        return commentMapper.queryAllComment(bingliId);
    }

    @Override
    public int queryNewcommentNum(Integer id) {
        return commentMapper.queryNewcommentNum(id);
    }

    @Override
    public Page<BingLiPoJo> queryNewComment(Integer doctorId, Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);
        List<BingLiPoJo> bingLiPoJoList=commentMapper.queryNewComment(doctorId);
        PageInfo<BingLiPoJo> pageInfo=new PageInfo<>(bingLiPoJoList);

        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
                pageInfo.getList(),pageInfo.getPages(),pageInfo.getTotal());

    }

    @Override
    public int updateCommentStatus(Integer bingliId,Integer role) {
        return commentMapper.updateCommentStatus(bingliId,role);
    }

    @Override
    public int deleteCommentById(Integer id) {
        redisTemplate.delete("commentList");
        return commentMapper.deleteCommentById(id);
    }
}
