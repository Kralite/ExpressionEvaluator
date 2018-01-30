package com.killover.expressionevaluator.operator.longNumber;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/3.
 */
public class LongLessthanOperator extends BinaryOperator<Long, Long, Boolean> {

    public LongLessthanOperator(){
        super(2);
    }

    @Override
    public Operand<Boolean> operate(Operand<Long> operand1, Operand<Long> operand2){
        return new BooleanOperand(operand1.value() < operand2.value());
    }
}