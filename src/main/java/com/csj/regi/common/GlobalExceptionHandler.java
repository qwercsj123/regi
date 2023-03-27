package com.csj.regi.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author:csj
 * @version:1.0
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    //异常处理的类型
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException e){
        log.error(e.getMessage());
        if(e.getMessage().contains("Duplicate entry")){
            String []split=e.getMessage().split(" ");
            String msg=split[2]+"已经存在";
            return Result.error(msg);
        }
        return Result.error("未知错误");
    }
    @ExceptionHandler(CustomException.class)
    public Result<String> exceptionHandler(CustomException e){
        return Result.error(e.getMessage());
    }
    @ExceptionHandler(FileNotFoundException.class)
    public Result<String> exceptionHandler(FileNotFoundException e){
        return Result.error("未找到该文件");
    }
}
