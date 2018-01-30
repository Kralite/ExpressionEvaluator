package com.killover.expressionevaluator.operator.wrapper;

import com.killover.expressionevaluator.operator.Operator;

/**
 * Created by killover on 2016/11/1.
 */
public abstract class OperatorWrapper extends Operator {
    private String symbol;

    public OperatorWrapper(String symbol, int priority){
        super(priority);
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
