package com.example.springnetty.netty.Exception;

/**
 * @program: spring-netty
 * @description: 异常处理
 * @author: ywd
 * @contact:1371690483@qq.com
 * @create: 2020-12-01 16:10
 **/
public class TuulException extends Exception {
    private int code;



    public TuulException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;

    }

    public TuulException(String message, int code) {
        super(message);
        this.code = code;

    }

    public TuulException(Throwable cause, int code) {
        super(cause);
        this.code = code;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
