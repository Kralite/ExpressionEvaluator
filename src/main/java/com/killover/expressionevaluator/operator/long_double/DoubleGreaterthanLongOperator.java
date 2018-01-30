package com.killover.expressionevaluator.operator.long_double;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/3.
 */
public class DoubleGreaterthanLongOperator extends BinaryOperator<Double, Long, Boolean> {
    public DoubleGreaterthanLongOperator(){
        super(2);
    }

    @Override
    public Operand<Boolean> operate(Operand<Double> operand1, Operand<Long> operand2){
        return new BooleanOperand(operand1.value() - operand2.value() > 0);
    }
}
