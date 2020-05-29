package com.online.microservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.microservice.entity.EduTeacher;
import com.online.microservice.entity.query.TeacherQuery;
import com.online.microservice.mapper.EduTeacherMapper;
import com.online.microservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author oxj
 * @since 2020-04-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if (StringUtils.isEmpty(teacherQuery)){
            baseMapper.selectPage(pageParam,queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }

        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }

        baseMapper.selectPage(pageParam,queryWrapper);
    }
}
