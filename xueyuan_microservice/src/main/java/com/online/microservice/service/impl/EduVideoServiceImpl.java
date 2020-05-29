package com.online.microservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.microservice.entity.EduVideo;
import com.online.microservice.entity.form.VideoInfoForm;
import com.online.microservice.handler.EduException;
import com.online.microservice.mapper.EduVideoMapper;
import com.online.microservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public boolean getCountByChapterId(String chapterId) {

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        Integer integer = baseMapper.selectCount(queryWrapper);

        return null != integer && integer > 0;
    }

    @Override
    public void saveVideoInfo(VideoInfoForm videoInfoForm) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm,eduVideo);
        boolean save = this.save(eduVideo);
        if(!save){
            throw new EduException(20001,"课时信息保存失败");
        }
    }

    @Override
    public VideoInfoForm getVideoInfoById(String id) {
        EduVideo video = this.getById(id);
        if (video == null){
            throw new EduException(20001,"数据不存在");
        }

        VideoInfoForm videoInfoForm = new VideoInfoForm();
        BeanUtils.copyProperties(video,videoInfoForm);
        return videoInfoForm;
    }

    @Override
    public void updateVideoInfoById(VideoInfoForm videoInfoForm) {
        EduVideo eduVideo = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm,eduVideo);
        boolean b = this.updateById(eduVideo);
        if(!b){
            throw new EduException(20001,"课时信息更新失败");
        }
    }

    @Override
    public Boolean removeVideoById(String id) {

        //删除视频资源 TODO

        Integer i = baseMapper.deleteById(id);
        return null != i && i > 0;
    }
}
