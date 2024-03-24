package com.jxufe.service;

import com.github.pagehelper.Page;
import com.jxufe.entity.Picture;

import java.util.List;

public interface PictureService {

    Page<Picture> listPictures(Long userId);

    int savePicture(Picture picture);

    Picture getPictureById(Long id);

    int updatePicture(Picture picture);

    void deletePicture(Long id);
}
