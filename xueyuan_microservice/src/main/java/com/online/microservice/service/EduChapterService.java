package com.online.microservice.service;

import com.online.microservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.microservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */
public interface EduChapterService extends IService<EduChapter> {
    boolean removeByCourseId(String courseId);

    List<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);
}
