package com.killover.expressionevaluator.operand.model;

/**
 * Created by killover on 2016/11/2.
 */
public class Variable {

    private String name;
    private Object value;

    public Variable(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
