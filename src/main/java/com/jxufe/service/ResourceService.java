package com.jxufe.service;

import com.jxufe.entity.Blog;
import com.jxufe.entity.Resource;
import com.jxufe.vo.ResourceQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResourceService {

    Resource getResource(Long id);

    Page<Resource> listResource(Pageable pageable, ResourceQuery resource);

    Page<Resource> listResource(Pageable pageable);

    List<Resource> listRecommendResourceTop(Integer size);

    Page<Resource> listResource(Pageable pageable, String query);

    Resource getAndConvert(Long id);

    Resource saveResource(Resource resource);

    Resource updateResource(Long id, Resource resource);

    void deleteResource(Long id);

    Integer updateViews(Long id);
}
