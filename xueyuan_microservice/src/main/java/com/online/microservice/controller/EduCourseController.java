package com.online.microservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.constant.R;
import com.online.microservice.entity.EduCourse;
import com.online.microservice.entity.form.CourseInfoForm;
import com.online.microservice.entity.query.CourseQuery;
import com.online.microservice.entity.vo.CoursePublishVo;
import com.online.microservice.service.EduCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */
@Api("课程管理")
@RestController
@CrossOrigin
@RequestMapping("/microservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //新增课程
    @PostMapping("/save")
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm){

        String courseId = eduCourseService.saveCourse(courseInfoForm);


        if (!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId",courseId);
        }else {
            return R.error().message("保存失败");
        }


    }

    //根据id查询课程
    @GetMapping("/query/{id}")
    public R getById(@PathVariable String id){
        CourseInfoForm courseInfoForm = eduCourseService.getCourseInfoFormById(id);
        return R.ok().data("item",courseInfoForm);
    }

    //更新课程
    @PutMapping("/update")
    public R updateById(@RequestBody CourseInfoForm courseInfoForm){
        eduCourseService.updateCourseInfoById(courseInfoForm);
        return R.ok();

    }

    //分页课程列表
    @GetMapping("/{page}/{limit}")
    public R pageQuery(@PathVariable Long page,
                       @PathVariable Long limit,
                       @RequestBody CourseQuery courseQuery){
        Page<EduCourse> coursePage = new Page<>(page, limit);

        eduCourseService.pageQuery(coursePage,courseQuery);

        List<EduCourse> records = coursePage.getRecords();

        long total = coursePage.getTotal();

        return R.ok().data("total",total).data("rows",records);

    }

    //根据Id删除课程
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable String id){
        boolean result = eduCourseService.removeCourseById(id);
        if (result){
            return R.ok();
        }
        else {
            return R.error().message("删除失败");
        }
    }


    //根据Id查询课程发布信息
    @GetMapping("{id}")
    public R queryPublishInfo(@PathVariable String id){

        CoursePublishVo courseInfoForm = eduCourseService.queryPublishInfoById(id);

        return R.ok().data("item",courseInfoForm);
    }

    //根据Id发布课程
    @PutMapping("/{id}")
    public R publishCourseById(@PathVariable String id){
        Boolean result = eduCourseService.publishCourseById(id);
        if (result){
            return R.ok();
        }else {
            return R.error().message("发布课程失败");
        }

    }


}

