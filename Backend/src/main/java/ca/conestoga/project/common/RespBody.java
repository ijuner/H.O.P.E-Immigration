package ca.conestoga.project.common;

/**
 * Encapsulate the API interface to return a unified format.
 *
 * @param <T> the data that response carried
 */
public class RespBody<T> {
    private T data;     // the data that response carried
    private int code;   // status code
    private String msg; // status message

    public static RespBody isOk() {
        return new RespBody<>().code(CodeEnum.SUCCESS.code).msg(CodeEnum.SUCCESS.msg);
    }

    public static RespBody isFail() {
        return new RespBody<>().code(CodeEnum.ERROR.code).msg(CodeEnum.ERROR.msg);
    }

    public RespBody<T> data(T data) {
        this.setData(data);
        return this;
    }

    public RespBody<?> msg(ErrorEnum errorEnum) {
        this.setMsg(errorEnum.getMessage());
        return this;
    }

    public RespBody<?> msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public RespBody<?> msg(Throwable e) {
        this.setMsg(e.toString());
        return this;
    }

    public RespBody<?> code(int code) {
        this.setCode(code);
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * the code used in response
     */
    public enum CodeEnum {
        SUCCESS(0, "success"),
        ERROR(1, "error");
        final int code;
        final String msg;

        CodeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return this.code;
        }

        public String getMsg() {
            return this.msg;
        }
    }
}