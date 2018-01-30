package com.killover.expressionevaluator.operator;

import com.killover.expressionevaluator.exception.ExpressionOperatorException;
import com.killover.expressionevaluator.operand.Operand;

/**
 * Created by killover on 2016/10/31.
 */
public abstract class BinaryOperator<TYPE1, TYPE2, TYPE3> extends Operator{

    public BinaryOperator(int priority){
        super(priority);
    }

    abstract public Operand<TYPE3> operate(Operand<TYPE1> operand1, Operand<TYPE2> operand2) throws ExpressionOperatorException;

}
