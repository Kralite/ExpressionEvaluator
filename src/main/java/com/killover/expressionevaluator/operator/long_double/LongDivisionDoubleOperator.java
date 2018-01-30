package com.killover.expressionevaluator.operator.long_double;

import com.killover.expressionevaluator.operand.DoubleOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/3.
 */
public class LongDivisionDoubleOperator extends BinaryOperator<Long, Double, Double> {
    public LongDivisionDoubleOperator(){
        super(4);
    }

    @Override
    public Operand<Double> operate(Operand<Long> operand1, Operand<Double> operand2){
        return new DoubleOperand(operand1.value() / operand2.value());
    }
}
