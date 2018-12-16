package pers.fs.framework.exception;

import pers.fs.framework.message.MessageEnum;

/**
 * 自定义异常基类
 *
 * @author FS
 */
public class BaseException extends RuntimeException {
    private String exCode;
    private String exMessage;

    public BaseException(MessageEnum messageEnum) {
        this.exCode = messageEnum.getCode();
        this.exMessage = messageEnum.getMessage();
    }

    private BaseException(String exCode, String exMessage) {
        this.exCode = exCode;
        this.exMessage = exMessage;
    }

    public static BaseException boxException(Exception e) {
        if (e instanceof BaseException) {
            return (BaseException) e;
        } else {
            return new BaseException(MessageEnum.FAILURE.getCode(), e.getMessage());
        }
    }

    public String getExCode() {
        return exCode;
    }

    public String getExMessage() {
        return exMessage;
    }
}
