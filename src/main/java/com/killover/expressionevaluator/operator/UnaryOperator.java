package com.killover.expressionevaluator.operator;

import com.killover.expressionevaluator.exception.ExpressionOperatorException;
import com.killover.expressionevaluator.operand.Operand;

/**
 * Created by killover on 2016/10/31.
 */
public abstract class UnaryOperator<TYPE1, TYPE2> extends Operator{

    public UnaryOperator(int priority){
        super(priority);
    }

    abstract public Operand<TYPE2> operate(Operand<TYPE1> operand) throws ExpressionOperatorException;
}
