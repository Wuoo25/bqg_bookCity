package com.w.message;


//封装返回的信息，便于转成json
public class ResponseData {
    private int code;
    private String msg;
    private Object data;

    public int getCode() { return code; }

    public void setCode(int code) { this.code = code; }

    public String getMsg() { return msg; }

    public void setMsg(String msg) { this.msg = msg; }

    public Object getData() { return data; }

    public void setData(Object data) { this.data = data; }

    @Override
    public String toString() {
        return "ResponseData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}