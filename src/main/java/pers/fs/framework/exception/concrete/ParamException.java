package pers.fs.framework.exception.concrete;

import pers.fs.framework.exception.BaseException;
import pers.fs.framework.message.MessageEnum;

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
