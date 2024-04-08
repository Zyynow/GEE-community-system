package com.jxufe.service;

import com.github.pagehelper.Page;
import com.jxufe.entity.Feedback;


public interface FeedbackService {

    public Page<Feedback> pageFeedbacks();

    void deleteFeedback(Long id);
}
