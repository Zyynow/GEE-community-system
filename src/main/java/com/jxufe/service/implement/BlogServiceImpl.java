package com.jxufe.service.implement;

import com.jxufe.dao.BlogRepository;
import com.jxufe.entity.*;
import com.jxufe.exception.NotFoundException;
import com.jxufe.service.BlogService;
import com.jxufe.utils.MarkdownUtils;
import com.jxufe.utils.MyBeanUtils;
import com.jxufe.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentServiceImpl commentService;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getById(id);
    }

    @Override
    public List<Blog> findBlogs(Integer id) {
        return blogRepository.findBlogsByForumId(id);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                /**
                 * jpa实现SQL动态查询
                 */
                if (blog.getTitle() != null && !"".equals(blog.getTitle())) {
                    predicateList.add(criteriaBuilder.like(root.<String>get("title"),
                            "%" + blog.getTitle() + "%")); //like(属性的名, 属性的值)
                }
                if (blog.getTypeId() != null) {
                    predicateList.add(criteriaBuilder.<Type>equal(root.get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicateList.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                query.where(predicateList.toArray(new Predicate[predicateList.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.getById(id);
        if (b == null) {
            throw new NotFoundException("不存在该博客");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog)); //把blog对象copy到b对象时，过滤掉为null的属性对象
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public List<Blog> listBlogTopById(Integer size, Long id) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTopById(id, pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {
        return blogRepository.findQuery(query, pageable);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.getById(id);
        if (blog == null) {
            throw new NotFoundException("博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        // 获取所有的一级评论
        List<Comment> commentList = commentService.findListByBlogId(id);
        b.setComments(commentList);
        // 获取所有的二级评论
        for (Comment comment : commentList) {
            comment.setChildComment(commentService.findSubListByParentId(comment.getId()));
        }
        return b;
    }

    @Override
    public Map<String, List<Blog>> archivesBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Override
    public Integer updateViews(Long id) {
        return blogRepository.updateViews(id);
    }

    @Override
    public List<Blog> getArchiveBlogs(Long userId) {
        return blogRepository.getArchiveBlogs(userId);
    }
}
