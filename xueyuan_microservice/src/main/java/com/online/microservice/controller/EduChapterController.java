package com.online.microservice.controller;


import com.online.constant.R;
import com.online.microservice.entity.EduChapter;
import com.online.microservice.entity.vo.ChapterVo;
import com.online.microservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api("课程章节管理")
@CrossOrigin
@RestController
@RequestMapping("/microservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //嵌套章节数据列表
    @GetMapping("/nestedList/{courseId}")
    public R nestedListByCourseId(@PathVariable String courseId){
        List<ChapterVo> chapterVoList = eduChapterService.nestedList(courseId);

        return R.ok().data("items",chapterVoList);
    }

    //新增章节
    @PostMapping
    public R save(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        if (save){
            return R.ok();
        }else {
            return R.error().message("新增章节失败");
        }
    }

    //根据Id查询章节
    @GetMapping("/{id}")
    public R getById(@PathVariable String id){

        EduChapter chapter = eduChapterService.getById(id);
        return R.ok().data("item",chapter);
    }

    //根据Id修改章节
    @PutMapping("/{id}")
    public R updateById(@PathVariable String id,
                        @RequestBody EduChapter chapter){
        chapter.setId(id);
        boolean b = eduChapterService.updateById(chapter);
        if (b){
            return R.ok();
        }else {
            return R.error().message("修改章节失败");
        }
    }

    //根据Id删除章节
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable String id){

        boolean result = eduChapterService.removeChapterById(id);
        if (result){
            return R.ok();
        }else {
            return R.error().message("删除失败");
        }
    }
}

