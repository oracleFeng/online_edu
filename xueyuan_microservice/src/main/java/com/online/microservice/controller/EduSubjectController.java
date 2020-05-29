package com.online.microservice.controller;


import com.online.constant.R;
import com.online.microservice.entity.EduSubject;
import com.online.microservice.service.EduSubjectService;
import com.online.microservice.entity.vo.SubjectNestedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author oxj
 * @since 2020-04-08
 */
@Api(description="课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/microservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加一级分类
    @PostMapping("/saveOneLevel")
    public R saveOneLevel(@RequestBody EduSubject eduSubject){
        boolean result = eduSubjectService.saveOneLevel(eduSubject);
        if (result){
            return R.ok();
        }else{
            return R.error().message("添加一级失败");
        }
    }

    //添加二级分类
    @PostMapping("saveTwoLevel")
    public R saveTwoLevel(@RequestBody EduSubject eduSubject){
        boolean result = eduSubjectService.saveTwoLevel(eduSubject);
        if (result){
            return R.ok();
        }
        else{
            return R.error().message("添加二级分类失败");
        }
    }


    @DeleteMapping("/{id}")
    public R removeById(@PathVariable String id){
        boolean b = eduSubjectService.deleteById(id);
        if (b){
            return R.ok();
        }else {
            return R.error().message("删除失败");
        }

    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("")
    public R nestedList(){
        List<SubjectNestedVo> subjectNestedVoList = eduSubjectService.nestedList();
        return R.ok().data("items",subjectNestedVoList);
    }

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("/import")
    public R add(@RequestParam("file") MultipartFile file) throws Exception{

        List<String> msg = eduSubjectService.importExcel(file);
        if (msg.size()==0){
            return R.ok();
        }else {
            return R.error().message("导入失败！");

        }


    }

    //测试id是否可以自动添加
//    @PostMapping("/add")
//    public R addSubject(){
//        EduSubject eduSubject = new EduSubject();
//        eduSubject.setTitle("html");
//        eduSubject.setParentId("4");
//        boolean save = eduSubjectService.save(eduSubject);
//        if (save){
//            return R.ok();
//        }
//        return R.error();
//
//    }

}

