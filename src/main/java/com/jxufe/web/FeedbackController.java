package com.jxufe.web;

import com.jxufe.entity.Feedback;
import com.jxufe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String doFeedback() {
        return "feedback";
    }

    @PostMapping("/send")
    public void doFeedbackSend(Feedback feedback, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        if (feedback.getContent().equals("") || feedback.getTitle().equals("")) {
            response.getWriter().print("<script language='javascript'>alert('内容请填写完整');" +
                    "window.location.href='/dev/feedback';</script>");
            return;
        } else {
            userService.sendFeedback(feedback);
            response.sendRedirect("/dev/feedback");
        }
    }

    @GetMapping("/return")
    public String doFeedbackReturn(HttpSession session) {
        return "redirect:" + session.getAttribute("lastPath");
    }
}
