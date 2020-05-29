package com.online.microservice.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author oxj
 * @create 2020-05-26
 */
@ApiModel("Course查询对象")
@Data
public class CourseQuery implements Serializable {

    private static final long serializable = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;
}
