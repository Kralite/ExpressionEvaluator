package com.killover.expressionevaluator.operator;

/**
 * Created by killover on 2016/10/31.
 */
public class LeftBracketOperator extends BracketOperator {

    private static final int OPERATOR_PRIORITY_LEFT_BRACKET = Integer.MIN_VALUE;

    private LeftBracketOperator(int priority){
        super(priority);
    }

    public LeftBracketOperator(){
        super(OPERATOR_PRIORITY_LEFT_BRACKET);
    }

}
