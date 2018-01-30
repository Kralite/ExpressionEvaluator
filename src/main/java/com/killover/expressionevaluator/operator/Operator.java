package com.killover.expressionevaluator.operator;

import com.killover.expressionevaluator.base.ExpressionElement;

/**
 * Created by killover on 2016/10/31.
 */
public abstract class Operator implements ExpressionElement {
    private int priority;

    public Operator(int priority){
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
