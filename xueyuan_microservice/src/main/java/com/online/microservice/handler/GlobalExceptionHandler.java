package com.online.microservice.handler;

import com.online.constant.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author oxj
 * @create 2020-04-04
 */
@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(Exception.class)
    public R exception(Exception e){
        e.printStackTrace();
        return R.error().message("统一异常处理");

    }

    @ExceptionHandler(ArithmeticException.class)
    public R exception(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("特定异常处理");

    }

    @ExceptionHandler(EduException.class)
    public R exception(EduException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());

    }
}
