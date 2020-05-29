package com.online.microservice.mapper;

import com.online.microservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.microservice.entity.form.CourseInfoForm;
import com.online.microservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {


    CoursePublishVo getCoursePublishVoById(String id);
}
