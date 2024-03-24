package com.jxufe.service.implement;

import com.github.pagehelper.Page;
import com.jxufe.dao.PictureMapper;
import com.jxufe.entity.Picture;
import com.jxufe.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int savePicture(Picture picture) {
        return pictureMapper.savePicture(picture);
    }

    @Override
    public Picture getPictureById(Long id) {
        return pictureMapper.getPictureById(id);
    }

    @Override
    public int updatePicture(Picture picture) {
        return pictureMapper.updatePicture(picture);
    }

    @Override
    public void deletePicture(Long id) {
        pictureMapper.deletePicture(id);
    }
}
