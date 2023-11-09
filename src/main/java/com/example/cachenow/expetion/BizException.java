package com.example.cachenow.expetion;

import com.example.cachenow.common.ErrorCode;

/**
 * 业务异常类
 * @Author: Ifela
 * @Date: 2023/11/9 22:19:03
 */
public class BizException extends RuntimeException{
    /**
     * 错误码
     */
    private final int code;
    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
