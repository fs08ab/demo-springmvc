package me.fs.framework.bean;

import com.alibaba.fastjson.JSONObject;
import me.fs.framework.exception.BaseException;
import me.fs.framework.message.MessageEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * controller返回数据的公共对象
 *
 * @author FS
 */
public class ResultModel implements Serializable {
    private String code;
    private String message;
    private Object data;

    public ResultModel(List<?> data) {
        this.code = MessageEnum.SUCCESS.getCode();
        this.message = MessageEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public ResultModel(Map<?, ?> data) {
        this.code = MessageEnum.SUCCESS.getCode();
        this.message = MessageEnum.SUCCESS.getMessage();
        this.data = data;
    }

    public ResultModel(BaseException exception) {
        this.code = exception.getExCode();
        this.message = exception.getExMessage();
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
