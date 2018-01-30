package com.killover.expressionevaluator.operator.bool;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/1.
 */
public class BooleanAndOperator extends BinaryOperator<Boolean, Boolean, Boolean> {

    private static final int OPERATOR_PRIORITY_BOOLEAN_AND = 1;

    public BooleanAndOperator(){
        super(OPERATOR_PRIORITY_BOOLEAN_AND);
    }

    @Override
    public Operand<Boolean> operate(Operand<Boolean> operand1, Operand<Boolean> operand2){
        return new BooleanOperand(operand1.value() && operand2.value());
    }

}
