package com.jxufe.service.implement;

import com.github.pagehelper.Page;
import com.jxufe.dao.FeedbackMapper;
import com.jxufe.entity.Feedback;
import com.jxufe.service.FeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public Page<Feedback> pageFeedbacks() {
        return feedbackMapper.pageFeedbacks();
    }

    @Override
    public void deleteFeedback(Long id) {
        feedbackMapper.deleteFeedback(id);
    }
}
