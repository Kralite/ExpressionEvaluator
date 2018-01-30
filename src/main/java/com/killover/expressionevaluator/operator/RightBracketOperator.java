package com.killover.expressionevaluator.operator;

/**
 * Created by killover on 2016/10/31.
 */
public class RightBracketOperator extends BracketOperator {

    private static final int OPERATOR_PRIORITY_RIGHT_BRACKET = Integer.MIN_VALUE;

    private RightBracketOperator(int priority){
        super(priority);
    }

    public RightBracketOperator(){
        super(OPERATOR_PRIORITY_RIGHT_BRACKET);
    }
}
