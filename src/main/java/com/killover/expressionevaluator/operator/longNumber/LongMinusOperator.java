package com.killover.expressionevaluator.operator.longNumber;

import com.killover.expressionevaluator.operand.LongOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/3.
 */
public class LongMinusOperator extends BinaryOperator<Long, Long, Long> {

    public LongMinusOperator(){
        super(3);
    }

    @Override
    public Operand<Long> operate(Operand<Long> operand1, Operand<Long> operand2){
        return new LongOperand(operand1.value() - operand2.value());
    }
}

