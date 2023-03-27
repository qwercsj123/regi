package com.csj.regi.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 这是返回给前端的数据的地方
 * @param <T>
 */
@Data
public class Result<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    //成功的时候调用的方法
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }


    //失败的时候调用的方法
    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

    //操作动态数据的方法
    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
