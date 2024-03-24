package com.jxufe.dao;

import com.github.pagehelper.Page;
import com.jxufe.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PictureMapper {

    Page<Picture> listPictures(Long userId);

    int savePicture(Picture picture);

    Picture getPictureById(Long id);

    int updatePicture(Picture picture);

    void deletePicture(Long id);
}
