package com.killover.expressionevaluator.operator.long_double;

import com.killover.expressionevaluator.operand.DoubleOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/3.
 */
public class DoubleDivisionLongOperator extends BinaryOperator<Double, Long, Double> {
    public DoubleDivisionLongOperator(){
        super(4);
    }

    @Override
    public Operand<Double> operate(Operand<Double> operand1, Operand<Long> operand2){
        return new DoubleOperand(operand1.value() / operand2.value());
    }
}
