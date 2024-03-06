package com.jxufe.service.implement;

import com.jxufe.dao.TagRepository;
import com.jxufe.dao.TypeRepository;
import com.jxufe.entity.Tag;
import com.jxufe.entity.Type;
import com.jxufe.exception.NotFoundException;
import com.jxufe.service.TagService;
import com.jxufe.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagRepository repository;

    @Transactional //事务操作
    @Override
    public Tag saveTag(Tag tag) {
        return repository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return repository.getById(id);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag tag1 = repository.getById(id);
        if (tag1 == null) {
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, tag1);
        return repository.save(tag1);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return repository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        //指定排序为降序和排序的数量
        Sort sort = Sort.by(Sort.Direction.DESC, "resources.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return repository.findTop(pageable);
    }
}
