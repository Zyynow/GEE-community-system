package com.jxufe.dao;

import com.jxufe.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {

    @Query("select r from Resource r")
    List<Resource> findTop(Pageable pageable);

    /*
        把findQuery方法的第二个参数交给注解@Query中的 ? 去进行模糊查询
     */
    @Query("select r from Resource r where r.title like ?1 or r.content like ?1")
    Page<Resource> findQuery(String query, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update t_resource set views = views + 1 where id = ?1", nativeQuery = true)
    Integer updateViews(Long id);
}
