package com.online.microservice.controller;


import com.online.constant.R;
import com.online.microservice.entity.form.VideoInfoForm;
import com.online.microservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */
@Api("课时管理")
@CrossOrigin
@RestController
@RequestMapping("/microservice/video")
public class EduVideoController {


    @Autowired
    private EduVideoService eduVideoService;

    //新增课时
    @PostMapping("/add")
    public R save(@RequestBody VideoInfoForm videoInfoForm){

        eduVideoService.saveVideoInfo(videoInfoForm);
        return R.ok();
    }

    //根据Id查询课时信息
    @GetMapping("/{id}")
    public R queryById(@PathVariable String id){

        VideoInfoForm videoInfoForm = eduVideoService.getVideoInfoById(id);
        return R.ok().data("item",videoInfoForm);
    }

    //根据Id更新课时信息
    @PutMapping("{id}")
    public R updateById(@PathVariable String id,
                        @RequestBody VideoInfoForm videoInfoForm){

        eduVideoService.updateVideoInfoById(videoInfoForm);
        return R.ok();
    }

    //根据Id删除课时信息
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable String id){
        Boolean result = eduVideoService.removeVideoById(id);
        if (result){
            return R.ok();
        }
        else {
            return R.error().message("删除失败");
        }
    }


}

