package com.jxufe.service.implement;

import com.github.pagehelper.Page;
import com.jxufe.dao.PictureMapper;
import com.jxufe.entity.Picture;
import com.jxufe.entity.User;
import com.jxufe.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public Page<Picture> listPictures(Long userId) {
        return pictureMapper.listPictures(userId);
    }

    @Override
    public int savePicture(Picture picture, HttpSession session) {
        if (picture.getUserId() == null) {
            picture.setUserId(((User) session.getAttribute("user")).getId());
        }
        picture.setPictureTime(new Date());
        picture.setCreateTime(new Date());
        return pictureMapper.savePicture(picture);
    }

    @Override
    public Picture getPictureById(Long id) {
        return pictureMapper.getPictureById(id);
    }

    @Override
    public int updatePicture(Picture picture) {
        picture.setUpdateTime(new Date());
        return pictureMapper.updatePicture(picture);
    }

    @Override
    public void deletePicture(Long id) {
        pictureMapper.deletePicture(id);
    }
}
