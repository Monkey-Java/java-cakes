package com.cakes.dto;

public class Comparison {

    private static final long serialVersionUID = 1L;

    private String field;

    private Object before;

    private Object after;

    private Object isUpdate;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getBefore() {
        return before;
    }

    public void setBefore(Object before) {
        this.before = before;
    }

    public Object getAfter() {
        return after;
    }

    public void setAfter(Object after) {
        this.after = after;
    }

    public Object getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Object isUpdate) {
        this.isUpdate = isUpdate;
    }
}
