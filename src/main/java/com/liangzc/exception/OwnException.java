package com.liangzc.exception;

import lombok.Data;

/**
 * @Auther: liangzc
 * @Date: 2024/2/20 11:34
 * @Description:
 */
public class OwnException extends Exception{

    public OwnException(String message) {
        super(message);
    }

    public OwnException(Throwable cause) {
        super(cause);
    }
}
