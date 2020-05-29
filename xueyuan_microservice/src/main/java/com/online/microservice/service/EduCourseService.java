package com.online.microservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.microservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.microservice.entity.form.CourseInfoForm;
import com.online.microservice.entity.query.CourseQuery;
import com.online.microservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoFormById(String id);

    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery);

    boolean removeCourseById(String id);

    CoursePublishVo queryPublishInfoById(String id);

    Boolean publishCourseById(String id);
}
