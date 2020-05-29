package com.online.microservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.microservice.Utills.ExcelImportUtil;
import com.online.microservice.entity.EduSubject;
import com.online.microservice.handler.EduException;
import com.online.microservice.mapper.EduSubjectMapper;
import com.online.microservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.microservice.entity.vo.SubjectNestedVo;
import com.online.microservice.entity.vo.SubjectVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author oxj
 * @since 2020-04-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    private EduSubject getByTitle(String title) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");
        return baseMapper.selectOne(queryWrapper);
    }


    private EduSubject getSubByTitle(String title, String parentId) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> importExcel(MultipartFile file) {
        List<String> msg = new ArrayList<>();
        try {

            ExcelImportUtil excelHSSFUtil = new ExcelImportUtil(file.getInputStream());
            Sheet sheet = excelHSSFUtil.getSheet();

            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                msg.add("请填写数据");
                return msg;
            }
            for (int rowNum = 1; rowNum <= rowCount; rowNum++) {

                Row rowData = sheet.getRow(rowNum);
                if (rowData != null) {// 行不为空

                    //一级分类名称
                    String levelOneValue = "";
                    Cell levelOneCell = rowData.getCell(0);
                    if(levelOneCell != null){
                        levelOneValue = excelHSSFUtil.getCellValue(levelOneCell,1);
                        if (StringUtils.isEmpty(levelOneValue)) {
                            msg.add("第" + rowNum + "行一级分类为空");
                            continue;
                        }
                    }

                    EduSubject subject = this.getByTitle(levelOneValue);
                    EduSubject subjectLevelOne = null;
                    String parentId = null;
                    if(subject == null){//创建一级分类
                        subjectLevelOne = new EduSubject();
                        subjectLevelOne.setTitle(levelOneValue);
                        subjectLevelOne.setSort(0);
                        baseMapper.insert(subjectLevelOne);//添加
                        parentId = subjectLevelOne.getId();
                    }else{
                        parentId = subject.getId();
                    }

                    //二级分类名称
                    String levelTwoValue = "";
                    Cell levelTwoCell = rowData.getCell(1);
                    if(levelTwoCell != null){
                        levelTwoValue = excelHSSFUtil.getCellValue(levelTwoCell,1);
                        if (StringUtils.isEmpty(levelTwoValue)) {
                            msg.add("第" + rowNum + "行二级分类为空");
                            continue;
                        }
                    }
                    EduSubject subjectSub = this.getSubByTitle(levelTwoValue, parentId);
                    EduSubject subjectLevelTwo = null;
                    if(subjectSub == null){//创建二级分类
                        subjectLevelTwo = new EduSubject();
                        subjectLevelTwo.setTitle(levelTwoValue);
                        subjectLevelTwo.setParentId(parentId);
                        subjectLevelTwo.setSort(0);
                        baseMapper.insert(subjectLevelTwo);//添加
                    }
                }
            }

        }catch (Exception e){
            //EXCEL_DATA_ERROR(false, 21005, "Excel数据导入错误");
            e.printStackTrace();
            throw new EduException(20001,"数据导入失败");
        }

        return msg;
    }

    @Override
    public List<SubjectNestedVo> nestedList() {
        //最终数据列表
        ArrayList<SubjectNestedVo> subjectNestedVos = new ArrayList<>();

        //获取一级分类数据
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",0);
        queryWrapper.orderByAsc("sort","id");
        List<EduSubject> eduSubjects = baseMapper.selectList(queryWrapper);

        //获取二级分类数据
        QueryWrapper<EduSubject> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.ne("parent_id",0);
        queryWrapper1.orderByAsc("sort","id");
        List<EduSubject> subSubjects = baseMapper.selectList(queryWrapper1);

        //填充一级类别vo对象
        int size = eduSubjects.size();
        for (int i = 0; i < size; i++) {
            EduSubject eduSubject = eduSubjects.get(i);
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(eduSubject,subjectNestedVo);
            subjectNestedVos.add(subjectNestedVo);

            //填充二级分类vo数据
            ArrayList<SubjectVo> subjectVos = new ArrayList<>();
            int size1 = subSubjects.size();
            for (int j = 0; j < size1; j++) {
                EduSubject subSubject = subSubjects.get(j);
                if(eduSubject.getId().equals(subSubject.getParentId())){
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject,subjectVo);
                    subjectVos.add(subjectVo);
                }

            }
            subjectNestedVo.setChildren(subjectVos);
        }
        return subjectNestedVos;

    }

    //删除分类
    @Override
    public boolean deleteById(String id) {

        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count>0){
            return false;
        }else {
            int i = baseMapper.deleteById(id);
            return i>0;
        }


    }

    //添加一级分类
    @Override
    public boolean saveOneLevel(EduSubject eduSubject) {
        EduSubject subjectLevelOne = this.getByTitle(eduSubject.getTitle());
        if (subjectLevelOne ==null){
            eduSubject.setParentId("0");
            int insert = baseMapper.insert(eduSubject);
            return insert>0;
        }else {
            throw new EduException(20001,"类别已存在");
        }

    }

    @Override
    public boolean saveTwoLevel(EduSubject eduSubject) {
        EduSubject subByTitle = this.getSubByTitle(eduSubject.getTitle(), eduSubject.getParentId());
        if (subByTitle == null){
            return baseMapper.insert(eduSubject)>0;
        }else{
            throw new EduException(20001,"类别已存在");
        }
    }
}
