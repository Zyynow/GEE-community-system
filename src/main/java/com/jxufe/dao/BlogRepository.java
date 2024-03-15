package com.jxufe.dao;

import com.jxufe.entity.ArchiveBlog;
import com.jxufe.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b")
    List<Blog> findTop(Pageable pageable);

    /*
        把findQuery方法的第二个参数交给注解@Query中的 ? 去进行模糊查询
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findQuery(String query, Pageable pageable);

    @Query("select function('date_format', b.updateTime, '%Y') as year from Blog b group by function('date_format', b.updateTime, '%Y') order by year desc")
    List<String> findGroupYear();

    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') = ?1")
    List<Blog> findByYear(String year);

    @Query(value = "select * from t_blog where forum_id = ?1", nativeQuery = true)
    List<Blog> findBlogsByForumId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update t_blog set views = views + 1 where id = ?1", nativeQuery = true)
    Integer updateViews(Long id);

    /**
     * 大概率有问题 TODO
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.user.id = ?1")
    List<Blog> findTopById(Long id, Pageable pageable);

    @Query(value = "select * from t_blog where user_id = ?1 order by create_time desc", nativeQuery = true)
    List<Blog> getArchiveBlogs(Long userId);
}
