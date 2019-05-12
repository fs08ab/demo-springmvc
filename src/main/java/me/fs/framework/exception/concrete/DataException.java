package me.fs.framework.exception.concrete;

import me.fs.framework.exception.BaseException;
import me.fs.framework.message.MessageEnum;

/**
 * 自定义数据处理异常
 *
 * @author FS
 */
public class DataException extends BaseException {
    public DataException() {
        super(MessageEnum.INVALID_DATA);
    }
}
