package com.ccydsz.cloudtest.model.UI;

public class ExceptionDescriptionModel {

    public enum CodeEnum{
        DidRegister(-10000,"已注册"),
        NoUser(-10001,"用户未注册"),
        ErrorPassword(-10002,"密码错误");



        private Integer code;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        private String message;
        private CodeEnum(Integer code,String message){
            this.code = code;
            this.message = message;

        }

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int code;
    private String message;
}
