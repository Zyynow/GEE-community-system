package com.jxufe.service.implement;

import com.jxufe.dao.TypeRepository;
import com.jxufe.entity.Blog;
import com.jxufe.entity.Type;
import com.jxufe.exception.NotFoundException;
import com.jxufe.service.TypeService;
import com.jxufe.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeRepository repository;

    @Transactional //事务操作
    @Override
    public Type saveType(Type type) {
        return repository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return repository.getById(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = repository.getById(id);
        if (type1 == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, type1);
        return repository.save(type1);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return repository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        //指定排序为降序和排序的数量
        Sort sort = Sort.by(Sort.Direction.DESC, "resources.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return repository.findTop(pageable);
    }
}
