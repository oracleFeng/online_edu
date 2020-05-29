package com.online.microservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.constant.R;
import com.online.microservice.entity.EduTeacher;
import com.online.microservice.entity.query.TeacherQuery;
import com.online.microservice.handler.EduException;
import com.online.microservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author oxj
 * @since 2020-04-02
 */
@CrossOrigin
@RestController
@RequestMapping("/microservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //根据id查询讲师
    @GetMapping("/getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        EduTeacher Teacher = eduTeacherService.getById(id);
        return R.ok().data("items",Teacher);
    }

    //根据id修改讲师
    @PostMapping("/updateTeacherInfo/{id}")
    public R updateTeacher(@PathVariable String id,
                           @RequestBody EduTeacher eduTeacher){

        boolean b = eduTeacherService.updateById(eduTeacher);

        if (b){
            return R.ok();
        }
        else {
            throw new EduException(3000,"修改讲师失败");
            //return R.error();
        }
    }

    //添加讲师
    @PostMapping("/saveTeacher")
    public R saveTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }
        else {
            return R.error();
        }
    }

    //带条件分页
    @PostMapping("/pageListConditional/{page}/{limit}")
    public R pageListConditional(@PathVariable Long page,
                                 @PathVariable Long limit,
                                 @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageParam = new Page<>(page, limit);

        eduTeacherService.pageQuery(pageParam,teacherQuery);

        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return R.ok().data("total",total).data("rows",records);
    }


    //简单分页
    @GetMapping("/pageList/{page}/{limit}")
    public R pageList(@PathVariable Long page,
                      @PathVariable Long limit){

        Page<EduTeacher> page1 = new Page<>(page, limit);
        eduTeacherService.page(page1,null);

        List<EduTeacher> records = page1.getRecords();
        long total = page1.getTotal();

        return R.ok().data("total",total).data("rows",records);

    }

    //写controller的时候要用rest风格，才能较好的使用swagger测
    @GetMapping("/all")
    public R getAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);

        return R.ok().data("items",list);
    }

    @GetMapping("/{id}")
    public R removeById(@PathVariable String id){

        eduTeacherService.removeById(id);
        return R.ok();
    }

}

