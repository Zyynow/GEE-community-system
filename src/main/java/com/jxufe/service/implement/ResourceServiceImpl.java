package com.jxufe.service.implement;

import com.jxufe.dao.ResourceRepository;
import com.jxufe.entity.Resource;
import com.jxufe.entity.Type;
import com.jxufe.exception.NotFoundException;
import com.jxufe.service.ResourceService;
import com.jxufe.utils.MarkdownUtils;
import com.jxufe.utils.MyBeanUtils;
import com.jxufe.vo.ResourceQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public Resource getResource(Long id) {
        Resource resource = resourceRepository.getById(id);
        if (resource == null) {
            throw new NotFoundException("该博客不存在");
        }
        return resource;
    }

    @Override
    public Page<Resource> listResource(Pageable pageable, ResourceQuery resource) {
        return resourceRepository.findAll(new Specification<Resource>() {
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                /**
                 * jpa实现SQL动态查询
                 */
                if (resource.getTitle() != null && !"".equals(resource.getTitle())) {
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"),
                            "%" + resource.getTitle() + "%")); //like(属性的名, 属性的值)
                }
                if (resource.getTypeId() != null) {
                    predicateList.add(criteriaBuilder.<Type>equal(root.get("type").get("id"), resource.getTypeId()));
                }
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Resource> listResource(Pageable pageable) {
        return resourceRepository.findAll(pageable);
    }

    @Override
    public List<Resource> listRecommendResourceTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return resourceRepository.findTop(pageable);
    }

    @Override
    public Page<Resource> listResource(Pageable pageable, String query) {
        return resourceRepository.findQuery(query, pageable);
    }

    @Override
    public Resource getAndConvert(Long id) {
        Resource resource = resourceRepository.getById(id);
        if (resource == null) {
            throw new NotFoundException("该博客不存在");
        }
        Resource b = new Resource();
        BeanUtils.copyProperties(resource, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
    }

    @Transactional
    @Override
    public Resource saveResource(Resource resource) {
        if (resource.getId() == null) {
            resource.setCreateTime(new Date());
            resource.setUpdateTime(new Date());
            resource.setViews(0);
        } else {
            resource.setUpdateTime(new Date());
        }
        return resourceRepository.save(resource);
    }

    @Override
    public Resource updateResource(Long id, Resource resource) {
        Resource b = resourceRepository.getById(id);
        if (b == null) {
            throw new NotFoundException("不存在该博客");
        }
        BeanUtils.copyProperties(resource, b, MyBeanUtils.getNullPropertyNames(resource)); //把blog对象copy到b对象时，过滤掉为null的属性对象
        b.setUpdateTime(new Date());
        return resourceRepository.save(b);
    }

    @Override
    public void deleteResource(Long id) {
        resourceRepository.deleteById(id);
    }

    @Override
    public Integer updateViews(Long id) {
        return resourceRepository.updateViews(id);
    }
}
