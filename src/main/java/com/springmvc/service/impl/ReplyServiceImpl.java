package com.springmvc.service.impl;

import com.springmvc.dao.ReplyMapper;
import com.springmvc.pojo.Reply;
import com.springmvc.service.ReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService {

    @Resource
    private ReplyMapper replyMapper;

    @Override
    public int insert(Reply record) {
        return replyMapper.insert(record);
    }

    @Override
    public Reply selectByPrimaryKey(Integer id) {
        return replyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Reply> selectByCommentId(Integer commentId) {
        return replyMapper.selectByCommentId(commentId);
    }

    @Override
    public List<Reply> selectAll() {
        return replyMapper.selectAll();
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return replyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Reply record) {
        return replyMapper.updateByPrimaryKey(record);
    }
}
