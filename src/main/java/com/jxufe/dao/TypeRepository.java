package com.jxufe.dao;

import com.jxufe.entity.Type;
import com.jxufe.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;

import java.util.List;

/**
 * 使用JPA
 * JpaRepository提供JPA相关的方法，如刷新持久化数据、批量删除等。
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);

    @Query("select t from Type t") //自定义分页查询
    List<Type> findTop(Pageable pageble);
}
