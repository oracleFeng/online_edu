package com.online.microservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.microservice.entity.EduChapter;
import com.online.microservice.entity.EduCourse;
import com.online.microservice.entity.EduCourseDescription;
import com.online.microservice.entity.form.CourseInfoForm;
import com.online.microservice.entity.query.CourseQuery;
import com.online.microservice.entity.vo.CoursePublishVo;
import com.online.microservice.handler.EduException;
import com.online.microservice.mapper.EduCourseMapper;
import com.online.microservice.service.EduChapterService;
import com.online.microservice.service.EduCourseDescriptionService;
import com.online.microservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.microservice.service.EduVideoService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */

@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;


    @Override
    @Transactional
    public String saveCourse(CourseInfoForm courseInfoForm) {

        EduCourse course = new EduCourse();
        course.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm,course);
        boolean save = this.save(course);
        if (!save){
            throw new EduException(20001,"课程信息保存失败");

        }
        //保存课程详细信息
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoForm.getDescription());
        description.setId(course.getId());

        boolean result = eduCourseDescriptionService.save(description);
        if(!result){
            throw new EduException(20001,"课程详细信息保存失败");

        }
        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoFormById(String id) {
        EduCourse course = this.getById(id);
        if (course==null){
            throw new EduException(20001,"数据不存在");

        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course,courseInfoForm);

        EduCourseDescription description = eduCourseDescriptionService.getById(id);
        if (description!= null){
            courseInfoForm.setDescription(description.getDescription());
        }
        return courseInfoForm;
    }

    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        //保存课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        boolean b = this.updateById(eduCourse);
        if (!b){
            throw new EduException(20001,"课程基本信息保存失败");
        }

        //保存课程详细信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(eduCourse.getId());

        boolean b1 = eduCourseDescriptionService.updateById(courseDescription);
        if(!b1){
            throw new EduException(20001,"课程详细信息保存失败");
        }
    }

    @Override
    public void pageQuery(Page<EduCourse> coursePage, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery==null){
            baseMapper.selectPage(coursePage,queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if(!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }

        if(!StringUtils.isEmpty(teacherId)){
            queryWrapper.eq("teacher_id",teacherId);
        }

        if(!StringUtils.isEmpty(subjectParentId)){
            queryWrapper.ge("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(coursePage,queryWrapper);
    }

    @Override
    public boolean removeCourseById(String id) {

        eduVideoService.removeByCourseId(id);
        eduChapterService.removeByCourseId(id);
        Integer i = baseMapper.deleteById(id);

        return null != i && i > 0;
    }

    @Override
    public CoursePublishVo queryPublishInfoById(String id) {
        return baseMapper.getCoursePublishVoById(id);
    }

    @Override
    public Boolean publishCourseById(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus(EduCourse.COURSE_NORMAL);
        Integer i = baseMapper.updateById(eduCourse);
        return null != i && i > 0;
    }
}
