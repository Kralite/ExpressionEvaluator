package com.killover.expressionevaluator.operand;

import com.killover.expressionevaluator.base.ExpressionElement;

/**
 * Created by killover on 2016/10/31.
 */
public abstract class Operand<T> implements ExpressionElement {

    private T value;

    public Operand(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }
}