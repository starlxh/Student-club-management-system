package com.liu.club_ms.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private Object data;
    private Long total; // 分页信息：总条数

    private Response(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data=data;
    }

    public static Response ok() {
        return new Response(Constants.OK_CODE, Constants.OK_MSG, null);
    }

    public static Response ok(Object data) {
        return new Response(Constants.OK_CODE, Constants.OK_MSG, data);
    }

    public static Response ok(long total, Object data) {
        return new Response(Constants.OK_CODE, Constants.OK_MSG, data, total);
    }

    public static Response ok(String message, Object data) {
        return new Response(Constants.OK_CODE, message, data);
    }

    public static Response fail(String message) {
        return new Response(Constants.FAIL_CODE, message, null);
    }

    public static Response fail(int errorCode, String message) {
        return new Response(errorCode, message, null);
    }
}