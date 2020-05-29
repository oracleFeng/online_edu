package com.online.microservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.microservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.microservice.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author oxj
 * @since 2020-04-02
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);
}
