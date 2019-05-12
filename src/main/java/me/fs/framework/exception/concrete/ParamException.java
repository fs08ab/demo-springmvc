package me.fs.framework.exception.concrete;

import me.fs.framework.exception.BaseException;
import me.fs.framework.message.MessageEnum;

/**
 * 自定义参数异常
 *
 * @author FS
 */
public class ParamException extends BaseException {
    public ParamException() {
        super(MessageEnum.INVALID_PARAM);
    }
}
