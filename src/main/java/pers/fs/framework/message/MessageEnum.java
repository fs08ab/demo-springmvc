package pers.fs.framework.message;

/**
 * 系统内的提示信息
 *
 * @author FS
 */
public enum MessageEnum {
    /**
     * 操作成功时的提示信息
     */
    SUCCESS("0", "成功"),
    /**
     * 操作失败时的提示信息
     */
    FAILURE("-1", "失败"),
    /**
     * 参数异常
     */
    INVALID_PARAM("-1", "参数不合法"),
    /**
     * 数据处理异常
     */
    INVALID_DATA("-1", "数据处理异常");

    private String code;
    private String message;

    MessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
