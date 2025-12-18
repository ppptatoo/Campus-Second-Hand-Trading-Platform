package com.springmvc.service;

import com.springmvc.pojo.Reply;
import java.util.List;

public interface ReplyService {
    int insert(Reply record);
    
    Reply selectByPrimaryKey(Integer id);
    
    List<Reply> selectByCommentId(Integer commentId);
    
    List<Reply> selectAll();
    
    int deleteByPrimaryKey(Integer id);
    
    int updateByPrimaryKey(Reply record);
}
