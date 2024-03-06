package com.jxufe.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class VerificationController{

    private static final int W = 120;
    private static final int H = 35;

    @GetMapping("/codeImage")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedImage image = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.YELLOW); //填充颜色
        graphics.fillRect(0, 0, W, H); //填充矩形
        graphics.setColor(Color.BLUE); //边框颜色
        graphics.drawRect(1, 1, W - 2, H - 2); //画边框
        String vCode = generateVCode(graphics, 4);
        drawLines(graphics, 12);

        req.getSession().setAttribute("vCode", vCode);

        //设置响应头通知浏览器以图片的形式打开
        resp.setContentType("image/jpeg");

        ImageIO.write(image, "jpg", resp.getOutputStream());
;    }

    /**
     * 划线
     * @param graphics
     * @param i 条数
     */
    public void drawLines(Graphics graphics, int i) {
        for (int j = 0; j < i; j++) {
            int x1 = getIntByRandom(0, W);
            int x2 = getIntByRandom(0, W);
            int y1 = getIntByRandom(0, H);
            int y2 = getIntByRandom(0, H);
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * 随机生成验证码
     * @param graphics
     * @param i
     * @return
     */
    public String generateVCode(Graphics graphics, int i) {
        //设置字体
        graphics.setFont(new Font("宋体",Font.BOLD,28));

        String str = "0123456789qwertyuiopasdfghjklzxcvbnm";
        String res = "";
        int start = 15;
        for (int j = 0; j < i; j++) {
            //设置颜色
            graphics.setColor(Color.RED);
            //设置字体的旋转角度
            int degree = new Random().nextInt() % 30;
            char c = str.charAt(getIntByRandom(0, 36));
            //正向角度
            ((Graphics2D) graphics).rotate(degree  * Math.PI / 180 , start,20);
            graphics.drawString(String.valueOf(c), start, 20);
            //反向角度
            ((Graphics2D) graphics).rotate((-1) * degree  * Math.PI / 180 , start, 20);
            start += 25;
            res += c;
        }
        return res;
    }

    public int getIntByRandom(int low, int high) {
        return (int) (Math.random() * (high - low) + low);
    }
}
