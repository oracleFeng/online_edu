package com.online.microservice.service;

import com.online.microservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.microservice.entity.vo.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author oxj
 * @since 2020-04-08
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importExcel(MultipartFile file);

    List<SubjectNestedVo> nestedList();

    boolean deleteById(String id);

    boolean saveOneLevel(EduSubject eduSubject);

    boolean saveTwoLevel(EduSubject eduSubject);
}
