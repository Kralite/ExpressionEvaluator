package com.killover.expressionevaluator.operator.doubleNumber;

import com.killover.expressionevaluator.operand.BooleanOperand;
import com.killover.expressionevaluator.operand.Operand;
import com.killover.expressionevaluator.operator.BinaryOperator;

/**
 * Created by killover on 2016/11/3.
 */
public class DoubleLessthanOperator extends BinaryOperator<Double, Double, Boolean> {

    public DoubleLessthanOperator(){
        super(2);
    }

    @Override
    public Operand<Boolean> operate(Operand<Double> operand1, Operand<Double> operand2){
        return new BooleanOperand(operand1.value() < operand2.value());
    }
}

