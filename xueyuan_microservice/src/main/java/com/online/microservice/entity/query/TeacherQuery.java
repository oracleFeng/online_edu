package com.online.microservice.entity.query;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @author oxj
 * @create 2020-04-03
 */

@Data
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer level;

    private String begin;

    private String end;


}
