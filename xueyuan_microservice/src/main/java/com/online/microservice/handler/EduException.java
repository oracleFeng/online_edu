package com.online.microservice.handler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author oxj
 * @create 2020-04-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduException extends RuntimeException {

    private Integer code;
    private String message;
}
