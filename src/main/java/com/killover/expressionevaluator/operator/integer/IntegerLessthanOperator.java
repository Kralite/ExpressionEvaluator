package com.killover.expressionevaluator.operator.integer;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/10/31.
 */
public class IntegerLessthanOperator extends BinaryOperator<Integer, Integer, Boolean> {

    private static final int OPERATOR_PRIORITY_INTEGER_LESSTHAN = 2;

    public IntegerLessthanOperator(){
        super(OPERATOR_PRIORITY_INTEGER_LESSTHAN);
    }

    @Override
    public Operand<Boolean> operate(Operand<Integer> operand1, Operand<Integer> operand2){
        return new BooleanOperand(operand1.value() < operand2.value());
    }
}
