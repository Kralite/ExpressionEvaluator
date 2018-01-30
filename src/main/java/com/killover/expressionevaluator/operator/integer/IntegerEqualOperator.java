package com.killover.expressionevaluator.operator.integer;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/1.
 */
public class IntegerEqualOperator extends BinaryOperator<Integer, Integer, Boolean> {

    private static final int OPERATOR_PRIORITY_INTEGER_EQUAL = 2;

    public IntegerEqualOperator(){
        super(OPERATOR_PRIORITY_INTEGER_EQUAL);
    }

    @Override
    public Operand<Boolean> operate(Operand<Integer> operand1, Operand<Integer> operand2){
        return new BooleanOperand(operand1.value() - operand2.value() == 0);
    }

}
