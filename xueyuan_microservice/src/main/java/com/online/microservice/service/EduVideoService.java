package com.online.microservice.service;

import com.online.microservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.microservice.entity.form.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean removeByCourseId(String courseId);

    boolean getCountByChapterId(String chapterId);

    void saveVideoInfo(VideoInfoForm videoInfoForm);

    VideoInfoForm getVideoInfoById(String id);

    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    Boolean removeVideoById(String id);
}
