package com.example.aletta.nokiaowerinternet.model;

public class ActionModel {

    private String target;
    private String action;
    private String detail;

    public ActionModel(String code, String action, String detail) {
        this.target = code;
        this.action = action;
        this.detail = detail;
    }

    public String getCode() {
        return target;
    }

    public void setCode(String code) {
        this.target = code;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
