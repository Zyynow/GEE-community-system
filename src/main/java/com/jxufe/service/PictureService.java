package com.jxufe.service;

import com.github.pagehelper.Page;
import com.jxufe.entity.Picture;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PictureService {

    Page<Picture> listPictures(Long userId);

    int savePicture(Picture picture, HttpSession session);

    Picture getPictureById(Long id);

    int updatePicture(Picture picture);

    void deletePicture(Long id);

    void deletePictureByUser(Long id);
}
