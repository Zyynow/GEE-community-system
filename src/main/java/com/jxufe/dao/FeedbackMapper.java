package com.jxufe.dao;

import com.github.pagehelper.Page;
import com.jxufe.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedbackMapper {
    Page<Feedback> pageFeedbacks();

    void deleteFeedback(Long id);
}
