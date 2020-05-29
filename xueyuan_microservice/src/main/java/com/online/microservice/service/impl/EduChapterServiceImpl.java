package com.online.microservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.microservice.entity.EduChapter;
import com.online.microservice.entity.EduVideo;
import com.online.microservice.entity.vo.ChapterVo;
import com.online.microservice.entity.vo.VideoVo;
import com.online.microservice.handler.EduException;
import com.online.microservice.mapper.EduChapterMapper;
import com.online.microservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.microservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author oxj
 * @since 2020-05-25
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public List<ChapterVo> nestedList(String courseId) {

        //最终要得到的数据列表
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        //获取章节信息
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        chapterQueryWrapper.orderByAsc("sort","id");
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);

        //获取小节信息
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.orderByAsc("sort","id");
        List<EduVideo> eduVideos = eduVideoService.list(queryWrapper);

        //填充数据
        int size = eduChapters.size();
        for (int i = 0; i < size; i++) {
            EduChapter eduChapter = eduChapters.get(i);

            //创建章节Vo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            chapterVos.add(chapterVo);

            //填充小节数据
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            int size1 = eduVideos.size();
            for (int j = 0; j < size1; j++) {
                EduVideo eduVideo = eduVideos.get(j);
                if (eduChapter.getId().equals(eduVideo.getChapterId())){
                    //创建小节Vo对象
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        }

        return chapterVos;
    }

    @Override
    public boolean removeChapterById(String id) {

        //根据id查询是否存在小节，如果有则提示用户尚有子节点
        if(eduVideoService.getCountByChapterId(id)){
            throw new EduException(20001,"该章节下还有小节，请先删除小节视频");
        }

        Integer i = baseMapper.deleteById(id);
        return null != i && i > 0;
    }
}
